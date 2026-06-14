package com.cb.sat.core.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cb.sat.config.SecurityUtil;
import com.cb.sat.core.audit.UserSesion;
import com.cb.sat.core.exception.UnauthorizedException;
import com.cb.sat.dto.model.auth.AutorizacionResponse;
import com.cb.sat.dto.util.GenericUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserSesion usersion;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.debug("doFilterInternal");
		String header = request.getHeader(SecurityUtil.HEADER_STRING);
		String username = null;
		String authToken = null;
		if (GenericUtil.isNotEmpty(header) && header.startsWith(SecurityUtil.TOKEN_PREFIX)) {
			authToken = header.replace(SecurityUtil.TOKEN_PREFIX, "");
			try {
				AutorizacionResponse tt = jwtTokenUtil.getInfoFromToken(authToken);
				if (GenericUtil.isNotEmpty(tt)) {
					username=tt.getUsuario();
					usersion.getRegistro().setUsuario(username);
					usersion.getRegistro().setUsuarioId(tt.getUsuarioId());
					usersion.getRegistro().setRolCodigo(tt.getRolCodigo());
				}
			} catch (IllegalArgumentException e) {
				throw new UnauthorizedException("usuario del Token");
			} catch (ExpiredJwtException e) {
				throw new UnauthorizedException("usuario del Token");
			}
		}  
		if (!GenericUtil.isEmptyWithTrim(username) && GenericUtil.isNull(SecurityContextHolder.getContext().getAuthentication())) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if (jwtTokenUtil.validateToken(authToken, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		chain.doFilter(request, response);
	}
}