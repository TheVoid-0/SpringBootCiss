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
	IMPLEMENTATION_ERROR("Erro inesperado na implementação!", HttpStatus.INTERNAL_SERVER_ERROR, Severity.ALTA),

	/**
	 * Código PIS/NIS é inválido!"
	 */
	INVALID_PIS("Código PIS/NIS é inválido!", HttpStatus.INTERNAL_SERVER_ERROR, Severity.BAIXA),

	/**
	 * Código PIS/NIS é inválido!
	 */
	ERROR_CREATION_JWT("Houve um erro desconhecido na criação de um token JWT", HttpStatus.INTERNAL_SERVER_ERROR,
			Severity.ALTA),

	/**
	 * Erro de implementação/utilização na @annotation {@link NivelAcesso}
	 */
	NIVEL_ACESSO_IMPL_ERROR(
			"Erro na implementação/utilização da anotação NivelAcesso, nenhuma condição prevista foi satisfeita",
			HttpStatus.INTERNAL_SERVER_ERROR, Severity.ALTA),

	/**
	 * O id passado ao NivelAcesso deve ser um Long!
	 */
	NIVEL_ACESSO_ID_INVALIDO("O campo especificado como contendo o id do usuário possui um valor inválido!",
			HttpStatus.INTERNAL_SERVER_ERROR, Severity.ALTA);

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
