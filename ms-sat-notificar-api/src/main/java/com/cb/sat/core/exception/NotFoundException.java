package com.cb.sat.core.exception;

public class NotFoundException extends IntegrationException {

    public static final int HTTP_STATUS = 404;

    private static final long serialVersionUID = 9202646495636296957L;

    public NotFoundException() {
    }

    public NotFoundException(String systemMessage){
        super(systemMessage);
    }

    public NotFoundException(String systemMessage, String userMessage){
        super(systemMessage, userMessage);
    }

    public NotFoundException(String systemMessage, String userMessage, String errorCode){
        super(systemMessage, userMessage, errorCode);
    }

    public NotFoundException(String systemMessage, String userMessage, String errorCode, Throwable cause) {
        super(systemMessage, userMessage, errorCode, cause);
    }
}
