package com.cb.sat.app.configuration.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.cb.sat.domain.configuration.mappers.EmpresaMapper;
import com.cb.sat.dto.model.Constantes;
import com.cb.sat.dto.util.DateUtil;
import com.cb.sat.dto.util.GenericUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cb.sat.app.configuration.repository.EmpresaFiltroRepository;
import com.cb.sat.app.configuration.repository.EmpresaRepository;
import com.cb.sat.app.configuration.service.EmpresaService;
import com.cb.sat.core.exception.InternalException;
import com.cb.sat.core.service.ServiceBase;
import com.cb.sat.domain.configuration.Empresa;
import com.cb.sat.domain.configuration.view.EmpresaDataGrid;

import com.cb.sat.dto.model.empresa.EmpresaFiltroRequest;
import com.cb.sat.dto.model.empresa.EmpresaRequest;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class EmpresaServiceImpl extends ServiceBase implements EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private EmpresaFiltroRepository empresaFiltroRepository;

	@Autowired
	private EmpresaMapper empresaMapper;

	@Override
	public Empresa get(UUID empresaId) {
		Empresa empresa = empresaRepository.findById(empresaId)
				.orElseThrow(() -> new InternalException("La empresa no se encuentra registrada"));
		return empresa;
	}

	@Override
	public List<EmpresaDataGrid> find(EmpresaFiltroRequest t) {
		Page<EmpresaDataGrid> page = empresaFiltroRepository.findAll(new Specification<EmpresaDataGrid>() {
			private static final long serialVersionUID = -8484953439524266381L;

			@Override
			public Predicate toPredicate(Root<EmpresaDataGrid> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!GenericUtil.isEmptyWithTrim(t.getRazonSocial())) {
					predicates.add(
							cb.like(cb.upper(root.get("razonSocial")), "%" + t.getRazonSocial().toUpperCase() + "%"));
				}
				if (!GenericUtil.isEmptyWithTrim(t.getRuc())) {
					predicates.add(cb.like(cb.upper(root.get("ruc")), "%" + t.getRuc().toUpperCase() + "%"));
				}
				if (!GenericUtil.isEmptyWithTrim(t.getPalabraClave())) {
					String search = "%" + t.getPalabraClave().toUpperCase() + "%";
					predicates.add(cb.or(cb.like(cb.upper(root.get("razonSocial")), search),
							cb.like(cb.upper(root.get("ruc")), search),
							cb.like(cb.upper(root.get("nombreComercial")), search),
							cb.like(cb.upper(root.get("direccion")), search)));
				}
				predicates.add(cb.equal(root.get("habilitado"), Constantes.HABILITADO));
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, PageRequest.of(t.getStart(), t.getLimit(), Direction.DESC, "fechaRegistro"));
		t.setTotalCount(page.getTotalElements());
		return page.getContent();
	}

	@Override
	public List<EmpresaDataGrid> load() {
		return empresaFiltroRepository.findAll(new Specification<EmpresaDataGrid>() {
			private static final long serialVersionUID = -8484953439514266381L;

			@Override
			public Predicate toPredicate(Root<EmpresaDataGrid> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(cb.equal(root.get("habilitado"), Constantes.HABILITADO));
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		});
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void saveOrUpdate(EmpresaRequest t) {
		if (GenericUtil.isNotEmpty(t.getEmpresaId())) {
			Empresa empresa = this.get(t.getEmpresaId());
			empresa.setRuc(t.getRuc());
			empresa.setRazonSocial(t.getRazonSocial());
			empresa.setNombreComercial(t.getNombreComercial());
			empresa.setDireccion(t.getDireccion());
			empresa.setDescripcion(t.getDescripcion());
			empresa.setModificado(DateUtil.getCurrentLocalDateTime());
			empresa.setModificadoPor(userSesion.getRegistro().getUsuario());
			empresaRepository.save(empresa);
		} else {
			Empresa empresa = empresaMapper.map(t);
			empresa.setHabilitado(Constantes.HABILITADO);
			empresa.setEstadoCodigo(Constantes.EstadoEmpresa.ACTIVO);
			empresa.setFechaRegistro(DateUtil.getCurrentLocalDateTime());
			empresa.setCreado(DateUtil.getCurrentLocalDateTime());
			empresa.setCreadoPor(userSesion.getRegistro().getUsuario());
			empresaRepository.save(empresa);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void delete(UUID empresaId) {
		Empresa empresa = this.get(empresaId);
		if (Constantes.EstadoEmpresa.ACTIVO.equals(empresa.getEstadoCodigo())) {
			empresa.setEstadoCodigo(Constantes.EstadoEmpresa.INACTIVO);
		} else {
			empresa.setEstadoCodigo(Constantes.EstadoEmpresa.ACTIVO);
		}
		empresa.setModificado(DateUtil.getCurrentLocalDateTime());
		empresa.setModificadoPor(userSesion.getRegistro().getUsuario());
		empresaRepository.save(empresa);
	}

}
