package com.cb.sat.core.exception;

public class ForbiddenException extends IntegrationException {

    public static final int HTTP_STATUS = 403;

    private static final long serialVersionUID = 3023349083605497576L;

    public ForbiddenException() {
    }

    public ForbiddenException(String systemMessage) {
        super(systemMessage);
    }

    public ForbiddenException(String systemMessage, String userMessage) {
        super(systemMessage, userMessage);
    }

    public ForbiddenException(String systemMessage, String userMessage, String errorCode) {
        super(systemMessage, userMessage, errorCode);
    }

    public ForbiddenException(String systemMessage, String userMessage, String errorCode, Throwable cause) {
        super(systemMessage, userMessage, errorCode, cause);
    }
}
