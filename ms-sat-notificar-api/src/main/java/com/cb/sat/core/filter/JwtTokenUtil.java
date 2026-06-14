package com.cb.sat.core.filter;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.cb.sat.config.SecurityUtil;
import com.cb.sat.dto.model.auth.AutorizacionResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6816353449960388060L;

	private static final SecretKey SIGNING_KEY = Jwts.SIG.HS256.key().build();

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public AutorizacionResponse getInfoFromToken(String token) {
		AutorizacionResponse autorizacionResponse = new AutorizacionResponse();
		Claims claims = getAllClaimsFromToken(token);
		autorizacionResponse.setUsuario(claims.getSubject());
		autorizacionResponse.setRolCodigo(claims.get("rolCodigo").toString());
		autorizacionResponse.setUsuarioId(UUID.fromString(claims.get("usuarioId").toString()));
		return autorizacionResponse;
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		Jws<Claims> jws = Jwts.parser().verifyWith(SIGNING_KEY).build().parseSignedClaims(token);
		return jws.getPayload();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String generateToken(AutorizacionResponse t) {
		return doGenerateToken(t);
	}

	private String doGenerateToken(AutorizacionResponse t) {
		log.debug("doGenerateToken: {}", new Gson().toJson(t));
		Instant now = Instant.now();
		Instant expiration = now.plusSeconds(SecurityUtil.ACCESS_TOKEN_VALIDITY_SECONDS);
		List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(t.getRolCodigo()));
		return Jwts.builder().subject(t.getUsuario()).issuer(SecurityUtil.ISSUER_INFO).claim("scopes", authorities)
				.claim("usuarioId", t.getUsuarioId()).claim("rolCodigo", t.getRolCodigo()).issuedAt(Date.from(now))
				.expiration(Date.from(expiration)).signWith(SIGNING_KEY).compact();
	}
}