package com.marco.desafiociss.dto;

import java.util.Date;

import com.marco.desafiociss.enums.NivelAcessoEnum;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * DTO para ser utilizado como Class Projection nos métodos abstratos do
 * Repository padrão
 *
 */
@Data
@AllArgsConstructor
public class UsuarioProjectionDTO {
	private Long id;

	private String nome;

	private String sobrenome;

	private String email;

	private String pis;

	private NivelAcessoEnum nivelAcesso;

	private Date dataCadastro;

	private Date dataAtualizacao;
}
