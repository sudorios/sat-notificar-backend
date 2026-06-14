package com.cb.sat.config;

public class SecurityUtil {
	public static final String ISSUER_INFO = "";
	public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60;
	public static final String SIGNING_KEY = "SECURITY2025";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String AUTENTICACION_PUBLIC = "/api/**";
	public static final String PUBLIC = "/api/web/**";
	public static final String PASWORD_INICIAL = "Heroca2026.";
    public static final String CONTENT_TYPE = "Content-Type";

	private SecurityUtil() {
	}

	public static class Rol {
		public static final String ROL_OWNER = "ROL_ADMINISTRADOR";
	}
}
