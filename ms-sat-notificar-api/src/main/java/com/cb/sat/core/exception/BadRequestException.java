package com.cb.sat.core.exception;

public class BadRequestException extends IntegrationException {

    private static final long serialVersionUID = -5052721472930481372L;

    public static final int HTTP_STATUS = 400;

    public BadRequestException() {
    }

    public BadRequestException(String systemMessage) {
        super(systemMessage);
    }

    public BadRequestException(String systemMessage, String userMessage) {
        super(systemMessage, userMessage);
    }

    public BadRequestException(String systemMessage, String userMessage, String errorCode) {
        super(systemMessage, userMessage, errorCode);
    }

    public BadRequestException(String systemMessage, String userMessage, String errorCode, Throwable cause) {
        super(systemMessage, userMessage, errorCode, cause);
    }

    public BadRequestException(String userMessage, Throwable cause) {
        super(userMessage, cause);
    }
}
