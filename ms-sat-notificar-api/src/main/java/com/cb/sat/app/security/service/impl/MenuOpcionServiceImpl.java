package com.cb.sat.app.security.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cb.sat.app.security.repository.AccesoRepository;
import com.cb.sat.app.security.repository.MenuOpcionRepository;
import com.cb.sat.app.security.service.MenuOpcionService;
import com.cb.sat.app.security.service.RolService;
import com.cb.sat.core.exception.InternalException;
import com.cb.sat.core.service.ServiceBase;
import com.cb.sat.domain.security.Acceso;
import com.cb.sat.domain.security.MenuMaestro;
import com.cb.sat.domain.security.MenuOpcion;
import com.cb.sat.domain.security.Rol;
import com.cb.sat.domain.security.mappers.MenuOpcionMapper;
import com.cb.sat.dto.model.Constantes;
import com.cb.sat.dto.model.menuOpcion.MenuOpcionRequest;
import com.cb.sat.dto.util.DateUtil;
import com.cb.sat.dto.util.GenericUtil;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class MenuOpcionServiceImpl extends ServiceBase implements MenuOpcionService {

	@Autowired
	private MenuOpcionRepository menuOpcionRepository;

	@Autowired
	private AccesoRepository accesoRepository;

	@Autowired
	private RolService rolService;

	@Autowired
	private MenuOpcionMapper menuOpcionMapper;

	@Override
	public MenuOpcion get(UUID menuOpcionId) {
		MenuOpcion menuOpcion = menuOpcionRepository.findById(menuOpcionId)
				.orElseThrow(() -> new InternalException("La opci\u00f3n no se encuentra registrada"));
		return menuOpcion;
	}

	@Override
	public List<MenuOpcion> load(UUID menuMaestroId) {
		return menuOpcionRepository.findAll(new Specification<MenuOpcion>() {

			private static final long serialVersionUID = 132325251525515L;

			@Override
			public Predicate toPredicate(Root<MenuOpcion> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(cb.equal(root.get("habilitado"), Constantes.HABILITADO));
				if (menuMaestroId != null) {
					predicates.add(cb.equal(root.get("menuMaestro").get("menuMaestroId"), menuMaestroId));
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, Sort.by(Direction.ASC, "creado"));
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void saveOrUpdate(MenuOpcionRequest t) {
		if (GenericUtil.isNotEmpty(t.getMenuOpcionId())) {
			MenuOpcion menuOpcion = this.get(t.getMenuOpcionId());
			menuOpcion.setCodigo(t.getCodigo());
			menuOpcion.setNombre(t.getNombre());
			menuOpcion.setDescripcion(t.getDescripcion());
			menuOpcion.setModificado(DateUtil.getCurrentLocalDateTime());
			menuOpcion.setModificadoPor(userSesion.getRegistro().getUsuario());
			menuOpcionRepository.save(menuOpcion);
		} else {
			MenuOpcion menuOpcion = menuOpcionMapper.map(t);
			menuOpcion.setMenuMaestro(new MenuMaestro(t.getMenuMaestroId()));
			menuOpcion.setHabilitado(Constantes.HABILITADO);
			menuOpcion.setCreado(DateUtil.getCurrentLocalDateTime());
			menuOpcion.setCreadoPor(userSesion.getRegistro().getUsuario());
			menuOpcionRepository.save(menuOpcion);
			this.saveAcceso(menuOpcion);
		}
	}

	private void saveAcceso(MenuOpcion menuOpcion) {
		List<Rol> collection = rolService.load();
		List<Acceso> accesolista = new ArrayList<>();
		collection.forEach(tt -> {
			Acceso acceso = new Acceso();
			acceso.setHabilitado(Constantes.INHABILITADO);
			acceso.setCreado(DateUtil.getCurrentLocalDateTime());
			acceso.setCreadoPor(userSesion.getRegistro().getUsuario());
			acceso.setMenuOpcion(menuOpcion);
			acceso.setRol(tt);
			accesolista.add(acceso);
		});
		accesoRepository.saveAll(accesolista);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void delete(UUID menuOpcionId) {
		MenuOpcion menuOpcion = this.get(menuOpcionId);
		if (Constantes.HABILITADO.equals(menuOpcion.getHabilitado())) {
			menuOpcion.setHabilitado(Constantes.INHABILITADO);
		} else {
			menuOpcion.setHabilitado(Constantes.HABILITADO);
		}
		menuOpcion.setModificado(DateUtil.getCurrentLocalDateTime());
		menuOpcion.setModificadoPor(userSesion.getRegistro().getUsuario());
		menuOpcionRepository.save(menuOpcion);
	}

}
