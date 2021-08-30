package com.marco.desafiociss.dto;

import java.util.Date;

import com.marco.desafiociss.enums.NivelAcessoEnum;

import lombok.Data;

@Data
public class UsuarioDTO {
	private Long id;
	
	private String nome;
	
	private String sobrenome;
	
	private String email;
	
	private String pis;
	
	private NivelAcessoEnum nivelAcesso;
	
	private Date dataCadastro;
	
	private Date dataAtualizacao;
}
