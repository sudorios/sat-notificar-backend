package com.cb.sat.core.exception;

public class InternalException extends IntegrationException {

	private static final long serialVersionUID = 4858873008587533621L;

	public static final int HTTP_STATUS = 500;

	public InternalException() {
	}

	public InternalException(String systemMessage) {
		super(systemMessage);
	}

	public InternalException(String systemMessage, String userMessage) {
		super(systemMessage, userMessage);
	}

	public InternalException(String systemMessage, String userMessage, String errorCode) {
		super(systemMessage, userMessage, errorCode);
	}

	public InternalException(String systemMessage, String userMessage, String errorCode, Throwable cause) {
		super(systemMessage, userMessage, errorCode, cause);
	}
}
