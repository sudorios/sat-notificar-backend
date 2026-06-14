package com.cb.sat.core.exception;

public class ServiceUnavailableException extends IntegrationException {

    public static final int HTTP_STATUS = 503;

    private static final long serialVersionUID = 8140771351041388134L;

    public ServiceUnavailableException() {
    }

    public ServiceUnavailableException(String systemMessage) {
        super(systemMessage);
    }

    public ServiceUnavailableException(String systemMessage, String userMessage) {
        super(systemMessage, userMessage);
    }

    public ServiceUnavailableException(String systemMessage, String userMessage, String errorCode) {
        super(systemMessage, userMessage, errorCode);
    }

    public ServiceUnavailableException(String systemMessage, String userMessage, String errorCode, Throwable cause) {
        super(systemMessage, userMessage, errorCode, cause);
    }
}
