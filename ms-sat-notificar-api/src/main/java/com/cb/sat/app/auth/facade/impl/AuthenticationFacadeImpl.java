package com.cb.sat.app.auth.facade.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.cb.sat.app.auth.facade.AuthenticationFacade;
import com.cb.sat.app.integration.ComponenteNotificacionService;
import com.cb.sat.app.security.service.UsuarioService;
import com.cb.sat.core.exception.InternalException;
import com.cb.sat.core.exception.UnauthorizedException;
import com.cb.sat.core.facade.FacadeBase;
import com.cb.sat.core.filter.JwtTokenUtil;
import com.cb.sat.domain.security.Usuario;
import com.cb.sat.domain.security.view.UsuarioDataGrid;
import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.Constantes;
import com.cb.sat.dto.model.auth.AutorizacionResponse;
import com.cb.sat.dto.model.auth.CambioPasswordRequest;
import com.cb.sat.dto.model.auth.LoginRequest;
import com.cb.sat.dto.model.auth.UsuarioRecuperarClaveRequest;
import com.cb.sat.dto.util.DateUtil;
import com.cb.sat.dto.util.GenericUtil;

@Component
public class AuthenticationFacadeImpl extends FacadeBase implements AuthenticationFacade {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private ComponenteNotificacionService componenteNotificacionService;

	@Override
	public AutorizacionResponse login(LoginRequest request) {
		try {
			if (GenericUtil.isEmptyWithTrim(request.getUsuario()))
				throw new UnauthorizedException("Ingrese el Usuario");
			UsuarioDataGrid usuario = usuarioService.loadUserByUsername(request.getUsuario().trim().toUpperCase());
			if (Constantes.EstadoUsuario.ACTIVO.equals(usuario.getEstadoCodigo())) {
				AutorizacionResponse autorizacionResponse = new AutorizacionResponse();
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
						request.getUsuario().trim().toUpperCase(), request.getPassword()));
				autorizacionResponse.setNombre(usuario.getNombreCompleto());
				autorizacionResponse.setUsuario(usuario.getUsuario());
				autorizacionResponse.setRolCodigo(usuario.getRolCodigo());
				autorizacionResponse.setUsuarioId(usuario.getUsuarioId());
				autorizacionResponse.setRol(usuario.getRol());
				autorizacionResponse.setToken(jwtTokenUtil.generateToken(autorizacionResponse));
				return autorizacionResponse;
			} else {
				StringBuilder builder = new StringBuilder();
				builder.append("Estimado: ");
				builder.append(usuario.getNombreCompleto());
				builder.append(", su cuenta de usuario se encuentra ");
				builder.append(usuario.getEstado());
				throw new UnauthorizedException(builder.toString());
			}
		} catch (Exception e) {
			launchException(e);
		}
		return null;
	}

	@Override
	public BaseOperacionResponse updatePassword(CambioPasswordRequest t) {
		this.validarPassword(t.getNuevoPassword(), t.getConfirmarPassword());
		usuarioService.updatePasswordExternal(t);
		return new BaseOperacionResponse(Constantes.SUCCESS, messageUpdate);
	}

	private void validarPassword(String nuevoPassword, String confirmarPassword) {
		if (GenericUtil.isNull(nuevoPassword) || GenericUtil.isNull(confirmarPassword)
				|| !nuevoPassword.equals(confirmarPassword)) {
			throw new InternalException("Las contraseñas no coinciden. Verifique e intente nuevamente.");
		}
	}

	@Override
	public BaseOperacionResponse recuperarClave(UsuarioRecuperarClaveRequest t) {

		Usuario usuario = usuarioService.getByCorreo(t.getCorreo().toLowerCase());
		usuario.setToken(UUID.randomUUID().toString());
		usuario.setFechaExpiracionToken(DateUtil.getCurrentLocalDateTime().plusDays(1));
		usuarioService.update(usuario);

		sendCorreoRecuperarClave(usuario);
		return new BaseOperacionResponse(Constantes.SUCCESS,
				"Al correo indicado se le ha remitido un link para la recuperación de clave");
	}

	private void sendCorreoRecuperarClave(Usuario usuario) {
		Map<String, Object> map = new HashMap<>();
		map.put(Constantes.Email.TO_EMAIL, usuario.getCorreo().trim().toLowerCase());
		map.put(Constantes.Email.SUBJECT_EMAIL, "[SECURITY] - Correo de confirmaci\u00F3n");
		StringBuilder sb = new StringBuilder();
		sb.append(GenericUtil.emptyIfStringNull(usuario.getNombres()));
		sb.append(Constantes.BLANK_SPACE);
		sb.append(GenericUtil.emptyIfStringNull(usuario.getApellidoPaterno()));
		sb.append(Constantes.BLANK_SPACE);
		sb.append(GenericUtil.emptyIfStringNull(usuario.getApellidoMaterno()));
		map.put("usuario", sb.toString());
		String urlBase = pathBaseFront + Constantes.PathUrl.ACTUALIZAR_PASSWORD + usuario.getToken();
		map.put("urlBase", urlBase);
		map.put("fechaApp", DateUtil.getCurrentLocalDate());
		componenteNotificacionService.sendChangePassword(map);
	}

}
