package com.marco.desafiociss.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.marco.desafiociss.enums.NivelAcessoEnum;

/**
 * Para essa anotação funcionar, a função anotada precisa possuir um objeto que
 * implementa {@link IAutenticacao} como parâmetro. Se esse requisito não for
 * cumprido, um {@code ImplementationException} poderá ser lançado <br>
 * <br>
 * 
 * @author Marco Moura
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NivelAcesso {

	/**
	 * Nivel de acesso que será exigido do usuário logado
	 */
	NivelAcessoEnum nivelAcessoRequerido() default NivelAcessoEnum.ADMIN;

	/**
	 * Quanto true, indica que o método anotado possui o Id de um usuário que possui
	 * privilégio de acesso mesmo que não possua o nivel de acesso específicado em
	 * {@code nivelAcessoRequerido}
	 */
	boolean hasUserId() default false;

	/**
	 * OBS: Só possui efeito caso {@code hasUserId} for TRUE.<br>
	 * Especifica o nome do campo na qual está o id do usuário
	 */
	String usuarioIdField() default "id";

}
