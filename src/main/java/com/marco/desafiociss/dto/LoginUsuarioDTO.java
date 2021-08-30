package com.marco.desafiociss.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginUsuarioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8446370901156632882L;

	@NotEmpty
	@Email
	public String email;

	@NotEmpty
	@Size(min = 4, max = 30)
	public String senha;

}
