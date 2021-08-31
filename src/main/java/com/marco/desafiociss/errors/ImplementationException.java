package com.marco.desafiociss.errors;

import org.springframework.http.HttpStatus;

import com.marco.desafiociss.errors.ErrorCode.Severity;

import lombok.Getter;

@Getter
public class ImplementationException extends HttpRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1007748991540638338L;

	public ImplementationException(String message, HttpStatus httpStatus, Severity severidade) {
		super(message, httpStatus, severidade);
	}

	public ImplementationException(String message, HttpStatus httpStatus, Severity severidade, Throwable error) {
		super(message, httpStatus, severidade, error);
	}

	public ImplementationException(ErrorCode errorCode) {
		super(errorCode);
	}

}
