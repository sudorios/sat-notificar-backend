package com.cb.sat.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

//import brave.Tracer;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@ResponseBody
@Slf4j
public class CoreExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BadRequestException badRequestException(BadRequestException exception) {
		log.warn(buildMessageLog(HttpStatus.BAD_REQUEST, exception.toString()), exception);
		return exception;
	}

	@ExceptionHandler(ForbiddenException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ForbiddenException forbiddenException(ForbiddenException exception) {
		log.info(buildMessageLog(HttpStatus.FORBIDDEN, exception.toString()));
		return exception;
	}

	@ExceptionHandler(InternalException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public InternalException internalException(InternalException exception) {
		log.error(buildMessageLog(HttpStatus.INTERNAL_SERVER_ERROR, exception.toString()), exception);
		return exception;
	}

	@ExceptionHandler(NoContentException.class)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public NoContentException noContentException(NoContentException exception) {
		log.error(buildMessageLog(HttpStatus.NO_CONTENT, exception.toString()), exception);
		return exception;
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public NotFoundException notFoundException(NotFoundException exception) {
		log.info(buildMessageLog(HttpStatus.NOT_FOUND, exception.toString()));
		return exception;
	}

	@ExceptionHandler(ServiceUnavailableException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public ServiceUnavailableException serviceUnavailableException(ServiceUnavailableException exception) {
		log.error(buildMessageLog(HttpStatus.SERVICE_UNAVAILABLE, exception.toString()), exception);
		return exception;
	}

	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public UnauthorizedException unauthorizedException(UnauthorizedException exception) {
		log.error(buildMessageLog(HttpStatus.UNAUTHORIZED, exception.toString()), exception);
		return exception;
	}

	@ExceptionHandler(IntegrationException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public IntegrationException integrationException(IntegrationException exception) {
		log.error(buildMessageLog(HttpStatus.INTERNAL_SERVER_ERROR, exception.toString()), exception);
		return exception;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public InternalException buildGenericException(Throwable exception) {
		InternalException internalException = new InternalException();
		internalException.setSystemMessage(exception.getMessage());
		internalException.setUserMessage("Error desconocido");
		log.error(buildMessageLog(HttpStatus.INTERNAL_SERVER_ERROR, "GenericException ".concat(exception.toString())),
				exception);
		return internalException;
	}

	private String buildMessageLog(HttpStatus status, String message) {
		return String.format("Response API <%d> <%s>", status.value(), message);
	}
}
