package com.marco.desafiociss.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.marco.desafiociss.enums.NivelAcessoEnum;

import lombok.Data;

@Entity
@Table(name = "usuario")
@SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1)
@Data
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
	@Column(name = "id")
	private Long id;

	@Column(name = "nome", length = 30)
	private String nome;

	@Column(name = "sobrenome", length = 50)
	private String sobrenome;

	@Column(name = "email")
	private String email;

	@Column(name = "senha")
	private String senha;

	@Column(name = "pis", length = 11)
	private String pis;

	@Column(name = "nivel_acesso")
	@Enumerated(EnumType.ORDINAL)
	private NivelAcessoEnum nivelAcesso;

	@Column(name = "cadastrado_em")
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	@Column(name = "atualizado_em")
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtualizacao;

}
