package com.cb.sat.core.filter;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cb.sat.core.audit.UserSesion;
import com.cb.sat.dto.util.GenericUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class SessionFilter implements Filter {

	private UserSesion usersion;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		usersion = (UserSesion) WebApplicationContextUtils
				.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean("userSesion");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String usuario = httpServletRequest.getHeader("usuario");
		String rolCodigo = httpServletRequest.getHeader("rolCodigo");
		String usuarioId = httpServletRequest.getHeader("usuarioId");
		if (GenericUtil.isNotEmpty(usuario)) {
			usersion.getRegistro().setUsuario(usuario);
		}
		if (GenericUtil.isNotEmpty(rolCodigo)) {
			usersion.getRegistro().setRolCodigo(rolCodigo);
		}

		if (GenericUtil.isNotEmpty(usuarioId)) {
			usersion.getRegistro().setUsuarioId(UUID.fromString(usuarioId));
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
