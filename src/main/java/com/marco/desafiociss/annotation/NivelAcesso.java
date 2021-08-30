package com.marco.desafiociss.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.marco.desafiociss.enums.NivelAcessoEnum;

/**
 * Para essa anotação funcionar, a função anotada precisa possuir um objeto que
 * implementa {@link IAutenticacao} como parâmetro. Se esse requisito não for
 * cumprido, um {@code BusinessServerException} poderá ser lançado <br>
 * <br>
 * TODO: Adicionar validação de recurso através do id, exemplo: se o
 * {@code IAutenticacao} possui um id igual ao id do recurso que está tentando
 * acessar, permitir o acesso, caso contrário, é necessário possui o
 * NivelAutenticacaoEnum requerido
 * 
 * @author Marco Moura
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NivelAcesso {

	NivelAcessoEnum nivelAcessoRequerido() default NivelAcessoEnum.ADMIN;

}
