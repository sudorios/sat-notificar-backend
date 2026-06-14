package com.cb.sat.app.configuration.service.impl;

import java.math.BigDecimal;
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

import com.cb.sat.app.configuration.repository.CatalogoRepository;
import com.cb.sat.app.configuration.service.CatalogoService;
import com.cb.sat.core.exception.InternalException;
import com.cb.sat.core.service.ServiceBase;
import com.cb.sat.domain.configuration.Catalogo;
import com.cb.sat.domain.configuration.mappers.CatalogoMapper;
import com.cb.sat.dto.model.Constantes;
import com.cb.sat.dto.model.catalogo.CatalogoFiltroRequest;
import com.cb.sat.dto.model.catalogo.CatalogoRequest;
import com.cb.sat.dto.util.DateUtil;
import com.cb.sat.dto.util.GenericUtil;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class CatalogoServiceImpl extends ServiceBase implements CatalogoService {

	@Autowired
	private CatalogoRepository catalogoRepository;

	@Autowired
	private CatalogoMapper catalogoMapper;

	@Override
	public List<Catalogo> find(CatalogoFiltroRequest t) {
		Page<Catalogo> page = catalogoRepository.findAll(new Specification<Catalogo>() {

			private static final long serialVersionUID = -8484953439524266381L;

			@Override
			public Predicate toPredicate(Root<Catalogo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (!GenericUtil.isEmptyWithTrim(t.getNombre())) {
					predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("nombre")),
							"%".concat(t.getNombre().toUpperCase()).concat("%")));
				}
				if (!GenericUtil.isEmptyWithTrim(t.getCodigo())) {

					predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("codigo")),
							"%".concat(t.getCodigo().toUpperCase()).concat("%")));
				}
				if (!GenericUtil.isEmptyWithTrim(t.getPrefijo())) {
					predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("prefijo")),
							"%".concat(t.getPrefijo().toUpperCase()).concat("%")));
				}
				if (!GenericUtil.isEmptyWithTrim(t.getHabilitado())) {
					predicates.add(criteriaBuilder.equal(root.get("habilitado"), t.getHabilitado()));
				}
				if (!GenericUtil.isEmptyWithTrim(t.getPalabraClave())) {
					String search = "%" + t.getPalabraClave().toUpperCase() + "%";
					Predicate orPredicate = criteriaBuilder.or(
							criteriaBuilder.like(criteriaBuilder.upper(root.get("nombre")), search),
							criteriaBuilder.like(criteriaBuilder.upper(root.get("codigo")), search),
							criteriaBuilder.like(criteriaBuilder.upper(root.get("prefijo")), search));
					predicates.add(orPredicate);
				}
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get("referenciaCodigo"))));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, PageRequest.of(t.getStart(), t.getLimit(), Direction.ASC, "orden"));
		t.setTotalCount(page.getTotalElements());
		return page.getContent();
	}

	@Override
	public Catalogo get(UUID catalogoId) {
		Optional<Catalogo> optional = catalogoRepository.findById(catalogoId);
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new InternalException("El catalogo no se encuentra registrado");
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void delete(UUID catalogoId) {
		Catalogo catalogo = this.get(catalogoId);
		if (Constantes.HABILITADO.equals(catalogo.getHabilitado())) {
			catalogo.setHabilitado(Constantes.INHABILITADO);
		} else {
			catalogo.setHabilitado(Constantes.HABILITADO);
		}
		catalogo.setModificado(DateUtil.getCurrentLocalDateTime());
		catalogo.setModificadoPor(userSesion.getRegistro().getUsuario());
		catalogoRepository.save(catalogo);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void saveOrUpdate(CatalogoRequest t) {
		if (GenericUtil.isNotEmpty(t.getCatalogoId())) {
			Catalogo catalogo = this.get(t.getCatalogoId());
			catalogo.setNombre(t.getNombre());
			catalogo.setDescripcion(t.getDescripcion());
			catalogo.setValor1(t.getValor1());
			catalogo.setValor2(t.getValor2());
			catalogo.setPrefijo(t.getPrefijo());
			catalogo.setModificado(DateUtil.getCurrentLocalDateTime());
			catalogo.setModificadoPor(userSesion.getRegistro().getUsuario());
			catalogoRepository.save(catalogo);
		} else {
			if (GenericUtil.isNotEmpty(t.getReferenciaCodigo())) {
				BigDecimal valorOrden = catalogoRepository.getCatalogoOrden(t.getReferenciaCodigo());
				BigDecimal bigDecimal = (GenericUtil.isNotNull(valorOrden)) ? valorOrden.add(BigDecimal.ONE)
						: BigDecimal.ONE;
				if (GenericUtil.isNotEmpty(bigDecimal) && bigDecimal.intValue() > 0) {
					Optional<Catalogo> optional = catalogoRepository.getByCodigo(t.getReferenciaCodigo());
					if (!optional.isPresent()) {
						throw new InternalException("Debe seleccionar una tabla Maestra");
					}
					if (GenericUtil.isEmptyWithTrim(optional.get().getPrefijo())) {
						throw new InternalException("El Maestro no tiene Prefijo");
					}
					StringBuilder builder = new StringBuilder();
					t.setOrden(bigDecimal);
					builder.append(optional.get().getPrefijo());
					builder.append(GenericUtil.fillZero(bigDecimal.intValue(), Constantes.LONGUITUD_CODIGO_CATALOGO));
					t.setCodigo(builder.toString());
					t.setPrefijo(t.getPrefijo());
					validarCatalogoItem(t);
				}
			} else {
				validarCatalogo(t);
				BigDecimal valorCantidad = catalogoRepository.getCatalogoOrden();
				BigDecimal bigDecimal = (GenericUtil.isNotNull(valorCantidad)) ? valorCantidad.add(BigDecimal.ONE)
						: BigDecimal.ONE;
				if (GenericUtil.isNotEmpty(bigDecimal) && bigDecimal.intValue() > 0) {
					t.setOrden(bigDecimal);
				}
			}
			Catalogo catalogo = catalogoMapper.map(t);
			catalogo.setHabilitado(Constantes.HABILITADO);
			catalogo.setCreado(DateUtil.getCurrentLocalDateTime());
			catalogo.setCreadoPor(userSesion.getRegistro().getUsuario());
			catalogoRepository.save(catalogo);
		}
	}

	private void validarCatalogo(CatalogoRequest t) {
		List<Catalogo> catalogosCodigos = catalogoRepository.findByCodigo(t.getCodigo());
		if (GenericUtil.isNotEmpty(catalogosCodigos)) {
			StringBuilder builder = new StringBuilder();
			builder.append("El codigo ya se encuentra utilizado por");
			builder.append(catalogosCodigos.get(0).getNombre());
			throw new InternalException(builder.toString());
		}
		List<Catalogo> catalogosPrefijos = catalogoRepository.findByPrefijo(t.getPrefijo());
		if (GenericUtil.isNotEmpty(catalogosPrefijos)) {
			StringBuilder builder = new StringBuilder();
			builder.append("El codigo ya se encuentra utilizado por");
			builder.append(catalogosPrefijos.get(0).getNombre());
			throw new InternalException(builder.toString());
		}

	}

	private void validarCatalogoItem(CatalogoRequest t) {
		List<Catalogo> catalogosCodigos = catalogoRepository.findByCodigo(t.getCodigo());
		if (GenericUtil.isNotEmpty(catalogosCodigos)) {
			StringBuilder builder = new StringBuilder();
			builder.append("El codigo ya se encuentra utilizado por");
			builder.append(catalogosCodigos.get(0).getNombre());
			throw new InternalException(builder.toString());
		}

	}

	@Override
	public List<Catalogo> findItem(CatalogoFiltroRequest t) {
		Page<Catalogo> page = catalogoRepository.findAll(new Specification<Catalogo>() {

			private static final long serialVersionUID = -2913045301571902124L;

			@Override
			public Predicate toPredicate(Root<Catalogo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (!GenericUtil.isEmptyWithTrim(t.getNombre())) {
					predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("nombre")),
							"%".concat(t.getNombre().toUpperCase()).concat("%")));
				}
				if (!GenericUtil.isEmptyWithTrim(t.getCodigo())) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.equal(root.get("referenciaCodigo"), t.getCodigo().trim().toUpperCase())));
				}
				predicates.add(criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("referenciaCodigo"))));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, PageRequest.of(t.getStart(), t.getLimit(), Direction.ASC, "orden"));
		t.setTotalCount(page.getTotalElements());
		return page.getContent();
	}

	@Override
	public List<Catalogo> search(CatalogoFiltroRequest t) {
		Page<Catalogo> page = catalogoRepository.findAll(new Specification<Catalogo>() {
			private static final long serialVersionUID = 2502949831020987898L;

			@Override
			public Predicate toPredicate(Root<Catalogo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (!GenericUtil.isEmptyWithTrim(t.getNombre())) {
					predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")),
							"%".concat(t.getNombre().toLowerCase()).concat("%")));
				}
				if (!GenericUtil.isEmptyWithTrim(t.getCodigo())) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.equal(root.get("codigo"), t.getCodigo().trim().toUpperCase())));
				}
				if (!GenericUtil.isEmptyWithTrim(t.getReferenciaCodigo())) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("referenciaCodigo"),
							t.getReferenciaCodigo().trim().toUpperCase())));
				}
				if (!GenericUtil.isEmptyWithTrim(t.getPrefijo())) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.equal(root.get("prefijo"), t.getPrefijo().trim().toUpperCase())));
				}
				if (!GenericUtil.isEmptyWithTrim(t.getHabilitado()) && "true".equals(t.getHabilitado())) {
					predicates.add(criteriaBuilder.equal(root.get("habilitado"), Constantes.HABILITADO));
				} else if (!GenericUtil.isEmptyWithTrim(t.getHabilitado())) {
					predicates.add(criteriaBuilder.equal(root.get("habilitado"), Constantes.INHABILITADO));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, PageRequest.of(t.getStart(), t.getLimit(), Sort.by(Direction.ASC, "nombre", "orden")));
		t.setTotalCount(page.getTotalElements());
		return page.getContent();
	}

	@Override
	public List<Catalogo> loadByReferenciaNombre(String referenciaCodigo) {
		return catalogoRepository.findAll(new Specification<Catalogo>() {

			private static final long serialVersionUID = -8484953439524266381L;

			@Override
			public Predicate toPredicate(Root<Catalogo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(criteriaBuilder.equal(root.get("habilitado"), Constantes.HABILITADO));
				predicates.add(criteriaBuilder.equal(root.get("referenciaCodigo"), referenciaCodigo));

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, Sort.by(Direction.ASC, "nombre"));
	}

	@Override
	public List<Catalogo> loadByReferenciaNombreIn(String referenciaCodigo, List<String> codigos) {
		return catalogoRepository.findAll(new Specification<Catalogo>() {

			private static final long serialVersionUID = -8484953439524266381L;

			@Override
			public Predicate toPredicate(Root<Catalogo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(criteriaBuilder.equal(root.get("habilitado"), Constantes.HABILITADO));
				predicates.add(criteriaBuilder.equal(root.get("referenciaCodigo"), referenciaCodigo));
				predicates.add(criteriaBuilder.in(root.get("codigo")).value(codigos));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, Sort.by(Direction.ASC, "nombre"));
	}

	@Override
	public List<Catalogo> loadByReferenciaOrden(String referenciaCodigo) {
		return catalogoRepository.findAll(new Specification<Catalogo>() {

			private static final long serialVersionUID = -8484953439524266381L;

			@Override
			public Predicate toPredicate(Root<Catalogo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(criteriaBuilder.equal(root.get("habilitado"), Constantes.HABILITADO));
				predicates.add(criteriaBuilder.equal(root.get("referenciaCodigo"), referenciaCodigo));

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, Sort.by(Direction.ASC, "orden"));
	}

	@Override
	public Catalogo getCatalogo(String referenciaCodigo, String codigo) {
		List<Catalogo> catalogos = catalogoRepository.findAll(new Specification<Catalogo>() {

			private static final long serialVersionUID = -8484953439524266381L;

			@Override
			public Predicate toPredicate(Root<Catalogo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(criteriaBuilder.equal(root.get("habilitado"), Constantes.HABILITADO));
				predicates.add(criteriaBuilder.equal(root.get("codigo"), codigo));
				predicates.add(criteriaBuilder.equal(root.get("referenciaCodigo"), referenciaCodigo));

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, Sort.by(Direction.ASC, "orden"));
		if (catalogos.isEmpty()) {
			throw new InternalException("No se tiene registrado el catalogo");
		}
		return catalogos.get(0);
	}

}
