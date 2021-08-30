package com.marco.desafiociss.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.querydsl.core.types.Order;

import lombok.Data;

@Data
public class FiltroUsuarioDTO {
	private Order orderDirection;
	private String orderField;
	@NotNull
	private Long page;
	private Date dataCriacao;
	private String nome;
	private String sobrenome;
	private String email;
	private String pis;
}
