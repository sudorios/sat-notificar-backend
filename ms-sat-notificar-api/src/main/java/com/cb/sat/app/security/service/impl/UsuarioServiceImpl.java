package com.cb.sat.app.security.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cb.sat.app.configuration.service.CatalogoService;
import com.cb.sat.app.integration.ComponenteNotificacionService;
import com.cb.sat.app.security.repository.UsuarioFiltroRepository;
import com.cb.sat.app.security.repository.UsuarioRepository;
import com.cb.sat.app.security.service.UsuarioService;
import com.cb.sat.config.SecurityUtil;
import com.cb.sat.core.exception.InternalException;
import com.cb.sat.core.service.ServiceBase;
import com.cb.sat.domain.configuration.Catalogo;
import com.cb.sat.domain.configuration.Empresa;
import com.cb.sat.domain.security.Rol;
import com.cb.sat.domain.security.Usuario;
import com.cb.sat.domain.security.mappers.UsuarioMapper;
import com.cb.sat.domain.security.view.UsuarioDataGrid;
import com.cb.sat.dto.model.Constantes;
import com.cb.sat.dto.model.auth.CambioPasswordRequest;
import com.cb.sat.dto.model.usuario.UsuarioCambioPasswordRequest;
import com.cb.sat.dto.model.usuario.UsuarioFiltroRequest;
import com.cb.sat.dto.model.usuario.UsuarioRequest;
import com.cb.sat.dto.model.usuario.UsuarioResetPasswordRequest;
import com.cb.sat.dto.util.DateUtil;
import com.cb.sat.dto.util.GenericUtil;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsuarioServiceImpl extends ServiceBase implements UsuarioService {

	@Autowired
	private UsuarioFiltroRepository usuarioFiltroRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ComponenteNotificacionService componenteNotificacionService;

	@Autowired
	private CatalogoService catalogoService;

	@Autowired
	private UsuarioMapper usuarioMapper;

	@Override
	public UsuarioDataGrid loadUserByUsername(String usuario) {
		Optional<UsuarioDataGrid> optional = usuarioFiltroRepository.loadUserByUsername(usuario);
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new InternalException("El usuario no se encuentra registrado");

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void updatePasswordExternal(CambioPasswordRequest t) {
		Usuario usuario = this.getByToken(t.getToken());
		usuario.setPassword(passwordEncoder.encode(t.getNuevoPassword()));
		usuario.setModificado(DateUtil.getCurrentLocalDateTime());
		usuarioRepository.save(usuario);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public Usuario getByToken(String token) {
		List<Usuario> collection = usuarioRepository.findAll(new Specification<Usuario>() {
			private static final long serialVersionUID = 2512512656623767771L;

			@Override
			public Predicate toPredicate(Root<Usuario> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(cb.equal(root.get("token"), token));
				predicates.add(
						cb.greaterThanOrEqualTo(root.get("fechaExpiracionToken"), DateUtil.getCurrentLocalDateTime()));
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		});
		if (GenericUtil.isEmpty(collection)) {
			throw new InternalException("El token ingresado es incorrecto o ya venció");
		}
		if (collection.size() > 1) {
			throw new InternalException(
					"Existe más de un registro con el mismo token, favor de comunicarse con el administrador");
		}
		return collection.get(0);
	}

	@Override
	public Usuario getByCorreo(String correo) {
		List<Usuario> collection = usuarioRepository.findAll(new Specification<Usuario>() {
			private static final long serialVersionUID = 2512512656623787771L;

			@Override
			public Predicate toPredicate(Root<Usuario> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(cb.equal(root.get("habilitado"), Constantes.HABILITADO));
				predicates.add(cb.equal(root.get("correo"), correo));
				predicates.add(cb.equal(root.get("estadoCodigo"), Constantes.EstadoUsuario.ACTIVO));
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		});
		if (collection.size() > 1) {
			throw new InternalException("Hay más de un usuario con ese correo. Comuníquese con un administrador");
		}
		if (collection.isEmpty()) {
			throw new InternalException("No hay un usuario activo registrado con el correo proporcionado: " + correo);
		}
		return collection.get(0);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void update(Usuario usuario) {
		usuario.setModificado(DateUtil.getCurrentLocalDateTime());
		usuario.setModificadoPor(userSesion.getRegistro().getUsuario());
		usuarioRepository.save(usuario);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void saveOrUpdate(UsuarioRequest t) {
		this.validarUsuario(t.getUsuario(), t.getUsuarioId());
		this.validarCorreo(t.getCorreo(), t.getUsuarioId());
		if (GenericUtil.isNotNull(t.getUsuarioId())) {
			Usuario usuario = this.get(t.getUsuarioId());
			if (GenericUtil.isNotNull(t.getRolId())) {
				usuario.setRol(new Rol(t.getRolId()));
			}
			if (GenericUtil.isNotNull(t.getEmpresaId())) {
				usuario.setEmpresa(new Empresa(t.getEmpresaId()));
			}
			usuario.setTelefono(t.getTelefono());
			usuario.setCorreo(t.getCorreo());
			usuario.setNombres(t.getNombres());
			usuario.setApellidoPaterno(t.getApellidoPaterno());
			usuario.setApellidoMaterno(t.getApellidoMaterno());
			usuario.setTipoDocumentoCodigo(t.getTipoDocumentoCodigo());
			usuario.setDocumento(t.getDocumento());
			usuario.setTelefono(t.getTelefono());
			usuario.setModificado(DateUtil.getCurrentLocalDateTime());
			usuario.setModificadoPor(userSesion.getRegistro().getUsuario());
			usuarioRepository.save(usuario);
		} else {
			Usuario usuario = usuarioMapper.map(t);
			if (GenericUtil.isNotNull(t.getRolId())) {
				usuario.setRol(new Rol(t.getRolId()));
			}
			if (GenericUtil.isNotNull(t.getEmpresaId())) {
				usuario.setEmpresa(new Empresa(t.getEmpresaId()));
			}
			usuario.setPassword(passwordEncoder.encode(SecurityUtil.PASWORD_INICIAL));
			usuario.setEstadoCodigo(Constantes.EstadoUsuario.ACTIVO);
			usuario.setHabilitado(Constantes.HABILITADO);
			usuario.setCreado(DateUtil.getCurrentLocalDateTime());
			usuario.setCreadoPor(userSesion.getRegistro().getUsuario());
			usuarioRepository.save(usuario);
			this.sendCorreoRegisterUser(usuario);
		}
	}

	private void validarCorreo(String correo, UUID usuarioId) {
		List<UsuarioDataGrid> usuarioList = usuarioFiltroRepository.findAll(new Specification<UsuarioDataGrid>() {

			private static final long serialVersionUID = -6220324727639358017L;

			@Override
			public Predicate toPredicate(Root<UsuarioDataGrid> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (GenericUtil.isNotNull(usuarioId)) {
					predicates.add(criteriaBuilder.notEqual(root.get("usuarioId"), usuarioId));
				}
				predicates.add(criteriaBuilder.equal(root.get("correo"), correo));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		});

		if (!usuarioList.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			builder.append("El correo ingresado: ");
			builder.append(correo);
			builder.append(" ya se encuentra registrado, le corresponde a la persona: ");
			builder.append(usuarioList.get(0).getNombreCompleto());
			throw new InternalException(builder.toString());
		}

	}

	private void sendCorreoRegisterUser(Usuario usuario) {
		Map<String, Object> map = new HashMap<>();
		map.put(Constantes.Email.TO_EMAIL, usuario.getCorreo());
		map.put(Constantes.Email.SUBJECT_EMAIL, "[HEROCA] - Registro usuario");
		StringBuilder sb = new StringBuilder();
		sb.append(GenericUtil.emptyIfStringNull(usuario.getNombres()));
		sb.append(Constantes.BLANK_SPACE);
		sb.append(GenericUtil.emptyIfStringNull(usuario.getApellidoPaterno()));
		sb.append(Constantes.BLANK_SPACE);
		sb.append(GenericUtil.emptyIfStringNull(usuario.getApellidoMaterno()));
		map.put("nombreCompleto", sb.toString());
		map.put("usuario", usuario.getUsuario());
		map.put("password", SecurityUtil.PASWORD_INICIAL);
		map.put("urlBase", pathBaseFront);
		map.put("fecha", new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()));
		componenteNotificacionService.sendEmailRegistroUsuario(map);
	}

	private void validarUsuario(String usuario, UUID usuarioId) {
		List<UsuarioDataGrid> usuarioList = usuarioFiltroRepository.findAll(new Specification<UsuarioDataGrid>() {

			private static final long serialVersionUID = -6220324727639358017L;

			@Override
			public Predicate toPredicate(Root<UsuarioDataGrid> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (GenericUtil.isNotNull(usuarioId)) {
					predicates.add(criteriaBuilder.notEqual(root.get("usuarioId"), usuarioId));
				}
				predicates.add(criteriaBuilder.equal(root.get("usuario"), usuario));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		});

		if (!usuarioList.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			builder.append("El usuario ingresado: ");
			builder.append(usuario);
			builder.append(" ya se encuentra registrado, le corresponde a la persona: ");
			builder.append(usuarioList.get(0).getNombreCompleto());
			throw new InternalException(builder.toString());
		}

	}

	@Override
	public Usuario get(UUID usuarioId) {
		Optional<Usuario> optional = usuarioRepository.findById(usuarioId);
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new InternalException("El usuario no se encuentra registrado");
	}

	@Override
	public List<UsuarioDataGrid> find(UsuarioFiltroRequest t) {
		Page<UsuarioDataGrid> page = usuarioFiltroRepository.findAll(new Specification<UsuarioDataGrid>() {
			private static final long serialVersionUID = 7484953439524266322L;

			@Override
			public Predicate toPredicate(Root<UsuarioDataGrid> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!GenericUtil.isEmptyWithTrim(t.getEstadoCodigo())) {
					predicates.add(cb.equal(root.get("estadoCodigo"), t.getEstadoCodigo()));
				}
				if (!GenericUtil.isEmptyWithTrim(t.getTipoDocumentoCodigo())) {
					predicates.add(cb.equal(root.get("tipoDocumentoCodigo"), t.getTipoDocumentoCodigo()));
				}
				if (GenericUtil.isNotNull(t.getRolId())) {
					predicates.add(cb.equal(root.get("rolId"), t.getRolId()));
				}
				if (!GenericUtil.isEmptyWithTrim(t.getNumeroDocumento())) {
					predicates.add(
							cb.like(cb.upper(root.get("documento")), "%" + t.getNumeroDocumento().toUpperCase() + "%"));
				}
				if (!GenericUtil.isEmptyWithTrim(t.getNombreCompleto())) {
					predicates.add(cb.like(cb.upper(root.get("nombreCompleto")),
							"%" + t.getNombreCompleto().toUpperCase() + "%"));
				}
				if (!GenericUtil.isEmptyWithTrim(t.getPalabraClave())) {
					String search = "%" + t.getPalabraClave().toUpperCase() + "%";
					predicates.add(cb.or(cb.like(cb.upper(root.get("nombreCompleto")), search),
							cb.like(cb.upper(root.get("documento")), search)));
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, PageRequest.of(t.getStart(), t.getLimit(), Direction.DESC, "fechaConsulta"));
		t.setTotalCount(page.getTotalElements());
		return page.getContent();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void updatePassword(UsuarioCambioPasswordRequest t) {
		log.info("Clave actual : {}", passwordEncoder.encode(t.getClaveActual()));
		Usuario usuario = this.get(userSesion.getRegistro().getUsuarioId());
		log.info("Clave BD : {}", usuario.getPassword());
		if (passwordEncoder.matches(t.getClaveActual(), usuario.getPassword())) {
			usuario.setPassword(passwordEncoder.encode(t.getNuevoPassword()));
			usuario.setModificado(DateUtil.getCurrentLocalDateTime());
			usuario.setModificadoPor(userSesion.getRegistro().getUsuario());
			usuarioRepository.save(usuario);
		} else {
			throw new InternalException("La clave actual ingresada es incorrecta");
		}
	}

	@Override
	public void resetPasword(UsuarioResetPasswordRequest t) {
		UsuarioDataGrid usuarioDataGrid = this.getById(t.getUsuarioId());
		if (Constantes.EstadoUsuario.ACTIVO.equals(usuarioDataGrid.getEstadoCodigo())) {
			Catalogo item = catalogoService.getCatalogo(Constantes.Catalogo.VALORES_CONSTANTES,
					Constantes.ValoresConstantes.CANTIDAD_DIAS_RESET_PASSWORD);
			Usuario usuario = this.get(t.getUsuarioId());
			usuario.setToken(UUID.randomUUID().toString());
			usuario.setFechaExpiracionToken(
					DateUtil.getCurrentLocalDateTime().plusDays(Integer.valueOf(item.getValor1())));
			usuario.setModificado(DateUtil.getCurrentLocalDateTime());
			usuario.setModificadoPor(userSesion.getRegistro().getUsuario());
			usuarioRepository.save(usuario);
			this.sendCorreoResetPassword(usuario);
		} else {
			StringBuilder builder = new StringBuilder();
			builder.append("El usuario se encuentra: ");
			builder.append(usuarioDataGrid.getEstado());
			throw new InternalException(builder.toString());
		}
	}

	private void sendCorreoResetPassword(Usuario usuario) {
		log.info("usuario: {}", usuario.getCorreo());
		log.info("usuarioid : {}", usuario.getUsuarioId());
		Map<String, Object> map = new HashMap<>();
		map.put(Constantes.Email.TO_EMAIL, usuario.getCorreo());
		map.put(Constantes.Email.SUBJECT_EMAIL, "[SECURITY] - Resetear Contraseña");
		String nombreCompleto = usuario.getNombres() + " " + usuario.getApellidoPaterno() + " "
				+ usuario.getApellidoMaterno();
		map.put("usuario", nombreCompleto);
		String urlBase = pathBaseFront + Constantes.PathUrl.ACTUALIZAR_PASSWORD + usuario.getToken();
		map.put("urlBase", urlBase);
		map.put("fechaApp", DateUtil.getCurrentLocalDate());
		log.info("map: " + map.toString());
	}

	@Override
	public UsuarioDataGrid getById(UUID usuarioId) {
		log.debug("Get:: {}", usuarioId);
		Optional<UsuarioDataGrid> optional = usuarioFiltroRepository.findById(usuarioId);
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new InternalException("El usuario no se encuentra registrado");
	}

}
