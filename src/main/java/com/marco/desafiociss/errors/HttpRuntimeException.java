package com.marco.desafiociss.errors;

import org.springframework.http.HttpStatus;

import com.marco.desafiociss.errors.ErrorCode.Severity;

import lombok.Getter;

@Getter
public abstract class HttpRuntimeException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4132067200869172276L;
	
	private final String message;
	private final HttpStatus httpStatus;
	private final Severity severidade;

	public HttpRuntimeException(String message, HttpStatus httpStatus, Severity severidade) {
		super();
		this.message = message;
		this.httpStatus = httpStatus;
		this.severidade = severidade;
	}

	public HttpRuntimeException(String message, HttpStatus httpStatus, Severity severidade, Throwable error) {
		super(message, error);
		this.message = message;
		this.httpStatus = httpStatus;
		this.severidade = severidade;
	}

	public HttpRuntimeException(ErrorCode errorCode) {
		super(errorCode.getCustomMessage());
		this.message = errorCode.getCustomMessage();
		this.httpStatus = errorCode.getHttpStatus();
		this.severidade = errorCode.getSeveridade();
	}

	@Override
	public String getMessage() {
		return message;
	}
}
