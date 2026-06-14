package com.cb.sat.app.security.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.cb.sat.app.security.repository.MenuRolFiltroRepository;
import com.cb.sat.app.security.repository.MenuRolRepository;
import com.cb.sat.app.security.service.MenuRolService;
import com.cb.sat.core.exception.InternalException;
import com.cb.sat.core.service.ServiceBase;
import com.cb.sat.domain.security.MenuRol;
import com.cb.sat.domain.security.view.MenuRolDataGrid;
import com.cb.sat.dto.model.Constantes;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class MenuRolServiceImpl extends ServiceBase implements MenuRolService {

	@Autowired
	private MenuRolFiltroRepository menuRolFiltroRepository;

	@Autowired
	private MenuRolRepository menuRolRepository;

	@Override
	public List<MenuRolDataGrid> loadMenuByRolId(String rolCodigo) {
		return menuRolFiltroRepository.findAll(new Specification<MenuRolDataGrid>() {
			private static final long serialVersionUID = 156216774378738L;

			@Override
			public Predicate toPredicate(Root<MenuRolDataGrid> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(cb.equal(root.get("rolCodigo"), rolCodigo));
				predicates.add(cb.isNull(root.get("referenciaId")));
				predicates.add(cb.equal(root.get("habilitado"), Constantes.HABILITADO));
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, Sort.by(Direction.ASC, "orden"));
	}

	@Override
	public List<MenuRolDataGrid> loadMenuHijo(String rolCodigo, UUID menuMaestroId) {
		return menuRolFiltroRepository.findAll(new Specification<MenuRolDataGrid>() {
			private static final long serialVersionUID = 12451663167778L;

			@Override
			public Predicate toPredicate(Root<MenuRolDataGrid> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(cb.equal(root.get("rolCodigo"), rolCodigo));
				predicates.add(cb.equal(root.get("referenciaId"), menuMaestroId));
				predicates.add(cb.equal(root.get("habilitado"), Constantes.HABILITADO));
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, Sort.by(Direction.ASC, "orden"));
	}

	@Override
	public MenuRol get(UUID menuRolId) {
		Optional<MenuRol> optional = menuRolRepository.findById(menuRolId);
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new InternalException("El menuRol no se encuentra registrado");
	}

}
