package com.cb.sat.core.exception;

public class NoContentException extends IntegrationException{

    private static final long serialVersionUID = 7208235114197277268L;

    public static final int HTTP_STATUS = 204;

    public NoContentException() {
    }

    public NoContentException(String systemMessage){
        super(systemMessage);
    }

    public NoContentException(String systemMessage, String userMessage){
        super(systemMessage, userMessage);
    }

    public NoContentException(String systemMessage, String userMessage, String errorCode){
        super(systemMessage, userMessage, errorCode);
    }

    public NoContentException(String systemMessage, String userMessage, String errorCode, Throwable cause) {
        super(systemMessage, userMessage, errorCode, cause);
    }
}
