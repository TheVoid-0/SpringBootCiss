package com.marco.desafiociss.errors;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.marco.desafiociss.errors.BusinessServerException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ErrorHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.debug("EXECUTANDO METODO handleMethodArgumentNotValid");

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {

			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { BusinessServerException.class })
	protected ResponseEntity<Object> handleRuntimeException(BusinessServerException ex, WebRequest request) {
		this.log(ex);
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), ex.getHttpStatus(), request);
	}

	private void log(BusinessServerException ex) {
		switch (ex.getSeveridade()) {
		case BAIXA: {
			// TODO: Checar se o environment é de produção
			log.error(ex.getMessage());
			break;
		}
		case MEDIA: {
			log.error(ex.getMessage());
			break;
		}
		case ALTA: {
			log.error(ex.getMessage(), ex);
			break;
		}

		}
	}

}
