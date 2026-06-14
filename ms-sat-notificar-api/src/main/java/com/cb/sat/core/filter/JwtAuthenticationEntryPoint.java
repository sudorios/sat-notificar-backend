package com.cb.sat.core.filter;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -90731367144543199L;
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
			throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		Map<String, String> error = new HashMap<>();
		error.put("codigo", "200");
		error.put("mensaje", "Se requiere autenticación para obtener Informaicón");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
		response.getOutputStream().println(objectMapper.writeValueAsString(error));
	}

}