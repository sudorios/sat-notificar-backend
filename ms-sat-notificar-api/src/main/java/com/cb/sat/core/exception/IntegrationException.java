package com.cb.sat.core.exception;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties({"cause", "stackTrace", "suppressed", "localizedMessage", "message"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Setter
@Getter
public abstract class IntegrationException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -9212021501376585574L;

    protected String traceId;
    protected String errorCode;
    protected String systemMessage;
    protected String userMessage;

    public IntegrationException() {
    }

    public IntegrationException(String systemMessage) {
        super(systemMessage);
        this.systemMessage = systemMessage;
    }

    public IntegrationException(String systemMessage, String userMessage) {
        super(systemMessage);
        this.systemMessage = systemMessage;
        this.userMessage = userMessage;
    }

    public IntegrationException(String systemMessage, String userMessage, String errorCode) {
        super(systemMessage);
        this.systemMessage = systemMessage;
        this.userMessage = userMessage;
        this.errorCode = errorCode;
    }

    public IntegrationException(String systemMessage, String userMessage, String errorCode, Throwable cause) {
        super(systemMessage, cause);
        this.systemMessage = systemMessage;
        this.userMessage = userMessage;
        this.errorCode = errorCode;
    }

    public IntegrationException(String userMessage,Throwable cause) {
        super(userMessage, cause);
        this.userMessage = userMessage;
    }
}
