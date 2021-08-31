package com.marco.desafiociss.errors;

import org.springframework.http.HttpStatus;

import com.marco.desafiociss.errors.ErrorCode.Severity;

import lombok.Getter;

@Getter
public class BusinessServerException extends HttpRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6331230898112587033L;

	public BusinessServerException(String message, HttpStatus httpStatus, Severity severidade) {
		super(message, httpStatus, severidade);
	}

	public BusinessServerException(String message, HttpStatus httpStatus, Severity severidade, Throwable error) {
		super(message, httpStatus, severidade, error);
	}

	public BusinessServerException(ErrorCode errorCode) {
		super(errorCode);
	}

}
