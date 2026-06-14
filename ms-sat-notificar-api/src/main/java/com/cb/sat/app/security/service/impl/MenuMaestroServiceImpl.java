package com.cb.sat.app.security.service.impl;

import java.math.BigDecimal;
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

import com.cb.sat.app.security.repository.MenuMaestroRepository;
import com.cb.sat.app.security.service.MenuMaestroService;
import com.cb.sat.core.exception.InternalException;
import com.cb.sat.core.service.ServiceBase;
import com.cb.sat.domain.security.MenuMaestro;
import com.cb.sat.domain.security.mappers.MenuMaestroMapper;
import com.cb.sat.dto.model.Constantes;
import com.cb.sat.dto.model.menuMaestro.MenuMaestroRequest;
import com.cb.sat.dto.util.DateUtil;
import com.cb.sat.dto.util.GenericUtil;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenuMaestroServiceImpl extends ServiceBase implements MenuMaestroService {

	@Autowired
	private MenuMaestroRepository menuMaestroRepository;

	@Autowired
	private MenuMaestroMapper menuMaestroMapper;

	@Override
	public MenuMaestro get(UUID menuMaestroId) {
		MenuMaestro menuMaestro = menuMaestroRepository.findById(menuMaestroId)
				.orElseThrow(() -> new InternalException("El men\u00FA no se encuentra registrado\r\n"));
		return menuMaestro;
	}

	@Override
	public List<MenuMaestro> load() {
		return menuMaestroRepository.findAll(new Specification<MenuMaestro>() {
			private static final long serialVersionUID = 8484953439524266321L;

			@Override
			public Predicate toPredicate(Root<MenuMaestro> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(cb.isNull(root.get("menuMaestro")));
				predicates.add(cb.equal(root.get("habilitado"), Constantes.HABILITADO));
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, Sort.by(Direction.ASC, "orden"));
	}

	@Override
	public List<MenuMaestro> loadItem(UUID referenciaId) {
		log.info("referenciaId: {}", referenciaId);
		return menuMaestroRepository.findAll(new Specification<MenuMaestro>() {
			private static final long serialVersionUID = 7484953439524266321L;
			@Override
			public Predicate toPredicate(Root<MenuMaestro> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();

				if (GenericUtil.isNotEmpty(referenciaId)) {
					predicates.add(cb.equal(root.get("menuMaestro").get("menuMaestroId"), referenciaId));
				}
				predicates.add(cb.equal(root.get("habilitado"), Constantes.HABILITADO));
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, Sort.by(Direction.ASC, "orden"));
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void saveOrUpdate(MenuMaestroRequest t) {
		if (GenericUtil.isNotEmpty(t.getMenuMaestroId())) {
			MenuMaestro menuMaestro = this.get(t.getMenuMaestroId());
			menuMaestro.setNombre(t.getNombre());
			menuMaestro.setDescripcion(t.getDescripcion());
			menuMaestro.setUrl(t.getUrl());
			menuMaestro.setIcono(t.getIcono());
			menuMaestro.setModificado(DateUtil.getCurrentLocalDateTime());
			menuMaestro.setModificadoPor(userSesion.getRegistro().getUsuario());
			menuMaestroRepository.save(menuMaestro);
		} else {
			if (GenericUtil.isNotEmpty(t.getReferenciaId())) {
				BigDecimal maxOrden = menuMaestroRepository.getOrdenItem(t.getReferenciaId());
				BigDecimal orden = GenericUtil.isNotNull(maxOrden) ? maxOrden.add(BigDecimal.ONE) : BigDecimal.ONE;
				t.setOrden(orden);
			} else {
				BigDecimal maxOrden = menuMaestroRepository.getOrden();
				BigDecimal orden = GenericUtil.isNotNull(maxOrden) ? maxOrden.add(BigDecimal.ONE) : BigDecimal.ONE;
				t.setOrden(orden);
			}
			MenuMaestro menuMaestro = menuMaestroMapper.map(t);
			if (GenericUtil.isNotEmpty(t.getReferenciaId())) {
				menuMaestro.setMenuMaestro(new MenuMaestro(t.getReferenciaId()));
			}
			menuMaestro.setHabilitado(Constantes.HABILITADO);
			menuMaestro.setCreado(DateUtil.getCurrentLocalDateTime());
			menuMaestro.setCreadoPor(userSesion.getRegistro().getUsuario());
			menuMaestroRepository.save(menuMaestro);
		}

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void delete(UUID menuMaestroId) {
		MenuMaestro menuMaestro = this.get(menuMaestroId);
		if (Constantes.HABILITADO.equals(menuMaestro.getHabilitado())) {
			menuMaestro.setHabilitado(Constantes.INHABILITADO);
		} else {
			menuMaestro.setHabilitado(Constantes.HABILITADO);
		}
		menuMaestro.setModificado(DateUtil.getCurrentLocalDateTime());
		menuMaestro.setModificadoPor(userSesion.getRegistro().getUsuario());
		menuMaestroRepository.save(menuMaestro);
	}

}
