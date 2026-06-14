package com.cb.sat.app.security.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cb.sat.app.security.repository.AccesoRepository;
import com.cb.sat.app.security.repository.MenuOpcionRepository;
import com.cb.sat.app.security.repository.RolRepository;
import com.cb.sat.app.security.service.RolService;
import com.cb.sat.core.exception.InternalException;
import com.cb.sat.core.service.ServiceBase;
import com.cb.sat.domain.security.Acceso;
import com.cb.sat.domain.security.MenuOpcion;
import com.cb.sat.domain.security.Rol;
import com.cb.sat.domain.security.mappers.RolMapper;
import com.cb.sat.dto.model.Constantes;
import com.cb.sat.dto.model.rol.RolFiltroRequest;
import com.cb.sat.dto.model.rol.RolRequest;
import com.cb.sat.dto.util.DateUtil;
import com.cb.sat.dto.util.GenericUtil;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class RolServiceImpl extends ServiceBase implements RolService {

	@Autowired
	private RolRepository rolRepository;

	@Autowired
	private MenuOpcionRepository menuOpcionRepository;

	@Autowired
	private AccesoRepository accesoRepository;

	@Autowired
	private RolMapper rolMapper;

	@Override
	public List<Rol> load() {
		return rolRepository.findAll(new Specification<Rol>() {
			private static final long serialVersionUID = 2502949831020987898L;

			@Override
			public Predicate toPredicate(Root<Rol> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				predicates
						.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("habilitado"), Constantes.HABILITADO)));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, Sort.by(Direction.ASC, "rolId"));
	}

	@Override
	public List<Rol> loadForUsuario() {
		return rolRepository.findAll(new Specification<Rol>() {
			private static final long serialVersionUID = 2502949831020987898L;

			@Override
			public Predicate toPredicate(Root<Rol> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				predicates
						.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("habilitado"), Constantes.HABILITADO)));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, Sort.by(Direction.ASC, "rolId"));
	}

	@Override
	public Rol getByCodigo(String rolCodigo) {
		Optional<Rol> optional = rolRepository.getByCodigo(rolCodigo);
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new InternalException("El rol no se encuentra registrado");
	}

	@Override
	public Rol get(UUID rolId) {
		Optional<Rol> optional = rolRepository.findById(rolId);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new InternalException("El Rol no se encuentra registrado");
		}

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void saveOrUpdate(RolRequest t) {
		this.validarNombreRol(t.getNombre(), t.getRolId());
		if (GenericUtil.isNotNull(t.getRolId())) {
			Rol rol = this.get(t.getRolId());
			rol.setCodigo(t.getCodigo());
			rol.setDescripcion(t.getDescripcion());
			rol.setModificadoPor(userSesion.getRegistro().getUsuario());
			rol.setModificado(DateUtil.getCurrentLocalDateTime());
			rolRepository.save(rol);
		} else {
			Rol rol = rolMapper.map(t);
			rol.setHabilitado(Constantes.HABILITADO);
			rol.setCreado(DateUtil.getCurrentLocalDateTime());
			rol.setCreadoPor(userSesion.getRegistro().getUsuario());
			rolRepository.save(rol);
			this.saveAccesoDefault(rol.getRolId());
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	private void saveAccesoDefault(UUID rolId) {
		List<MenuOpcion> accesoLista = new ArrayList<>();
		accesoLista = menuOpcionRepository.findByMenuOpcionActivo(Constantes.HABILITADO);
		List<Acceso> accesolista = new ArrayList<>();
		Rol rol = new Rol(rolId);
		for (MenuOpcion opciones : accesoLista) {
			Acceso acceso = new Acceso();
			acceso.setHabilitado(Constantes.INHABILITADO);
			acceso.setCreado(DateUtil.getCurrentLocalDateTime());
			acceso.setCreadoPor(userSesion.getRegistro().getUsuario());
			MenuOpcion menuOpcion = new MenuOpcion();
			menuOpcion.setMenuOpcionId(opciones.getMenuOpcionId());
			acceso.setMenuOpcion(menuOpcion);
			acceso.setRol(rol);
			accesolista.add(acceso);
		}
		accesoRepository.saveAll(accesolista);
	}

	public void validarNombreRol(String nombreRol, UUID rolId) {
		List<Rol> roles = rolRepository.findAll(new Specification<Rol>() {
			private static final long serialVersionUID = -6220324727639358017L;

			@Override
			public Predicate toPredicate(Root<Rol> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (GenericUtil.isNotNull(rolId)) {
					predicates.add(criteriaBuilder.notEqual(root.get("rolId"), rolId));
				}
				predicates.add(criteriaBuilder.equal(root.get("nombre"), nombreRol));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
		if (!roles.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			builder.append("El nombre del rol ingresado: ");
			builder.append(nombreRol);
			builder.append(" ya se encuentra registrado.");
			throw new InternalException(builder.toString());
		}
	}

	@Override
	public List<Rol> find(RolFiltroRequest t) {
		Page<Rol> page = (Page<Rol>) rolRepository.findAll(new Specification<Rol>() {
			private static final long serialVersionUID = 3708378945197883042L;

			@Override
			public Predicate toPredicate(Root<Rol> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (!GenericUtil.isEmptyWithTrim(t.getCodigo())) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.get("codigo"), "%".concat(t.getCodigo()).concat("%"))));
				}
				if (!GenericUtil.isEmptyWithTrim(t.getNombre())) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.get("nombre"), "%".concat(t.getNombre()).concat("%"))));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, PageRequest.of(t.getStart(), t.getLimit(), Direction.ASC, "nombre"));
		t.setTotalCount(page.getTotalElements());
		return page.getContent();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void delete(UUID rolId) {
		Rol rol = this.get(rolId);
		if (Constantes.HABILITADO.equals(rol.getHabilitado())) {
			rol.setHabilitado(Constantes.INHABILITADO);
		} else {
			rol.setHabilitado(Constantes.HABILITADO);
		}
		rol.setModificado(DateUtil.getCurrentLocalDateTime());
		rol.setModificadoPor(userSesion.getRegistro().getUsuario());
		rolRepository.save(rol);
	}
}
