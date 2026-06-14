package com.cb.sat.core.exception;

public class UnauthorizedException extends IntegrationException {

	public static final int HTTP_STATUS = 401;

	private static final long serialVersionUID = 8846498816998640444L;

	public UnauthorizedException() {
	}

	public UnauthorizedException(String systemMessage) {
		super(systemMessage);
	}

	public UnauthorizedException(String systemMessage, String userMessage) {
		super(systemMessage, userMessage);
	}

	public UnauthorizedException(String systemMessage, String userMessage, String errorCode) {
		super(systemMessage, userMessage, errorCode);
	}

	public UnauthorizedException(String systemMessage, String userMessage, String errorCode, Throwable cause) {
		super(systemMessage, userMessage, errorCode, cause);
	}
}
