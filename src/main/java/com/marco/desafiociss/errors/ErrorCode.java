package com.marco.desafiociss.errors;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * Código de erro com uma mensagem, um StatusHttp e uma severidade atrelada:<br>
 * 
 * 1: Códigos com severidade 1 não serão logados<br>
 * 2: Códicos com severidade 2 Terão sua mensagem logada<br>
 * 3: Códigos com severidade 3 terão todo o StackTrace logado<br>
 * OBS: Em um ambiente que não é o de produção todas as severidades terão suas
 * mensagens logadas
 * 
 * @author Marco Moura
 */
@Getter
public enum ErrorCode {

	/**
	 * Usuário não foi encontrado
	 */
	USER_NOT_FOUND("Usuário não foi encontrado", HttpStatus.NOT_FOUND, Severity.BAIXA),

	/**
	 * Usuário já cadastrado
	 */
	USER_ALREADY_EXISTS("Usuário já cadastrado", HttpStatus.BAD_REQUEST, Severity.BAIXA),

	/**
	 * Senha inválida
	 */
	INVALID_PASSWORD("Senha inválida!", HttpStatus.BAD_REQUEST, Severity.BAIXA),

	/**
	 * Nível de privilégio insuficiente para esse recurso
	 */
	ACCESS_DENIED("Nível de privilégio insuficiente para esse recurso", HttpStatus.FORBIDDEN, Severity.BAIXA),

	/**
	 * Erro inesperado na implementação!"
	 */
	IMPLEMENTATION_ERROR("Erro inesperado na implementação!", HttpStatus.INTERNAL_SERVER_ERROR, Severity.ALTA);

	private final String customMessage;
	private final HttpStatus httpStatus;
	private final Severity severidade;

	ErrorCode(String customMessage, HttpStatus httpStatus, Severity severidade) {
		this.customMessage = customMessage;
		this.httpStatus = httpStatus;
		this.severidade = severidade;
	}

	public static enum Severity {
		BAIXA(1), MEDIA(2), ALTA(3);

		private final int code;

		Severity(int code) {
			this.code = code;
		}

		public int getCode() {
			return this.code;
		}
	}
}
