package com.marco.desafiociss.security;

import com.marco.desafiociss.enums.NivelAcessoEnum;

/**
 * Interface que impõem os atributos necessários em um objeto de
 * autenticação.<br>
 * É a interface responsável por garantir que os métodos de criação, resolução
 * de token e verificação de permissão, funcionem corretamente e manipulem
 * objetos com as propriedades certas. <br>
 * <br>
 * É obrigatória a implementação dessa interface em um Objeto de Autenticação
 * 
 * @author Marco Moura
 */
public interface IAutenticacao {
	abstract Long getId();

	abstract NivelAcessoEnum getNivelAcesso();

	abstract void setNivelAcesso(NivelAcessoEnum nivelAcesso);

	abstract String getJwtToken();

	abstract void setJwtToken(String token);
}
