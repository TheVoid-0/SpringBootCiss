package com.marco.desafiociss.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.marco.desafiociss.enums.NivelAcessoEnum;

import lombok.Data;

@Data
public class EditarUsuarioDTO {
	@Size(min = 2, max = 30)
	private String nome;

	@Size(min = 2, max = 30)
	private String sobrenome;

	@Email
	private String email;

	//@Size(min = 4, max = 30)
	//private String senha;

	@Size(min = 11, max = 11)
	private String pis;

	private NivelAcessoEnum nivelAcesso;
}
