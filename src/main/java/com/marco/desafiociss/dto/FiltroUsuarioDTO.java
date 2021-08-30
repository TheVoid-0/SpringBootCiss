package com.marco.desafiociss.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class FiltroUsuarioDTO {
	private String orderDirection;
	private String orderField;
	@NotNull
	private Long page;
	private Date dataCadastro;
	private String nome;
	private String sobrenome;
	private String email;
	private String pis;
}
