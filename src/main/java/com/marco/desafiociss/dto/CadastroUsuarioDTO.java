package com.marco.desafiociss.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.marco.desafiociss.enums.NivelAcessoEnum;

import lombok.Data;

@Data
public class CadastroUsuarioDTO {
	@NotEmpty
	@Size(min = 2, max = 30)
	private String nome;

	@NotEmpty
	@Size(min = 2, max = 30)
	private String sobrenome;

	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	@Size(min = 4, max = 30)
	private String senha;
	
	@NotEmpty
	@Size(min = 11, max = 11)
	private String pis;
	
	private NivelAcessoEnum nivelAcesso;
}
