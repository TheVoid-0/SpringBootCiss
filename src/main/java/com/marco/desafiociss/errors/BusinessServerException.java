package com.marco.desafiociss.errors;

import org.springframework.http.HttpStatus;

import com.marco.desafiociss.errors.ErrorCode.Severity;

import lombok.Getter;

@Getter
public class BusinessServerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6331230898112587033L;

	private final String message;
	private final HttpStatus httpStatus;
	private final Severity severidade;

	public BusinessServerException(String message, HttpStatus httpStatus, Severity severidade) {
		super();
		this.message = message;
		this.httpStatus = httpStatus;
		this.severidade = severidade;
	}

	public BusinessServerException(String message, HttpStatus httpStatus, Severity severidade, Throwable error) {
		super(message, error);
		this.message = message;
		this.httpStatus = httpStatus;
		this.severidade = severidade;
	}

	public BusinessServerException(ErrorCode errorCode) {
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
