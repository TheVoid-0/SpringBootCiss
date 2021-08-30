package com.marco.desafiociss.repository.custom.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.marco.desafiociss.domain.QUsuario;
import com.marco.desafiociss.domain.Usuario;
import com.marco.desafiociss.dto.FiltroUsuarioDTO;
import com.marco.desafiociss.dto.PageDTO;
import com.marco.desafiociss.repository.custom.UsuarioRepositoryCustom;
import com.marco.desafiociss.util.QueryDslUtil;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

public class UsuarioRepositoryCustomImpl implements UsuarioRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public <T> PageDTO<T> filtrarUsuarios(FiltroUsuarioDTO filtroUsuarioDTO, Class<T> projectionType) {
		JPAQuery<Usuario> query = new JPAQuery<>(entityManager);
		JPAQuery<Usuario> queryTotal = new JPAQuery<>(entityManager);
		QUsuario qUsuario = QUsuario.usuario;

		Long limit = 10L; // limite fixo por enquanto
		query.limit(limit);
		query.offset(limit * (filtroUsuarioDTO.getPage() - 1L));

		Order order = filtroUsuarioDTO.getOrderDirection();
		query.from(qUsuario);

		if (order != null) {
			query.orderBy(QueryDslUtil.getSortedColumn(order, filtroUsuarioDTO.getOrderField(), qUsuario));
		} else {
			query.orderBy(qUsuario.nome.asc());
		}

		if (filtroUsuarioDTO.getNome() != null) {
			query.where(qUsuario.nome.like(filtroUsuarioDTO.getNome()));
		}
		if (filtroUsuarioDTO.getSobrenome() != null) {
			query.where(qUsuario.sobrenome.like(filtroUsuarioDTO.getSobrenome()));
		}
		if (filtroUsuarioDTO.getEmail() != null) {
			query.where(qUsuario.email.like(filtroUsuarioDTO.getEmail()));
		}
		if (filtroUsuarioDTO.getPis() != null) {
			query.where(qUsuario.pis.like(filtroUsuarioDTO.getPis()));
		}
		if (filtroUsuarioDTO.getDataCriacao() != null) {
			query.where(qUsuario.dataCadastro.between(new Date(), filtroUsuarioDTO.getDataCriacao()));
		}

		PageDTO<T> pageDTO = new PageDTO<T>();
		pageDTO.setContent(query.select(Projections.bean(projectionType, qUsuario.id, qUsuario.nome, qUsuario.sobrenome,
				qUsuario.email, qUsuario.pis, qUsuario.dataCadastro, qUsuario.dataAtualizacao)).fetch());

		pageDTO.setPage(filtroUsuarioDTO.getPage());

		// Depreciado :( Apesar de que provavelmente funciona normalmente para uma query
		// simples e sem parâmetros
		// pageDTO.setTotalCount(queryTotal.from(qUsuario).fetchCount());

		return pageDTO;
	}

}
