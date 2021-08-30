package com.marco.desafiociss.dto;

import com.marco.desafiociss.enums.NivelAcessoEnum;
import com.marco.desafiociss.security.IAutenticacao;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AutenticacaoUsuarioDTO implements IAutenticacao {
	private String jwtToken;
	private Long id;
	private String nome;
	private String sobrenome;
	private String email;
	private NivelAcessoEnum nivelAcesso;
	private String pis;
	
	public AutenticacaoUsuarioDTO(Long id, String nome, String sobrenome, String email, NivelAcessoEnum nivelAcesso,
			String pis) {
		super();
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.email = email;
		this.nivelAcesso = nivelAcesso;
		this.pis = pis;
	}
}
