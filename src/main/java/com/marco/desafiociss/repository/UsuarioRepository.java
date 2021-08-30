package com.marco.desafiociss.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.marco.desafiociss.domain.Usuario;
import com.marco.desafiociss.repository.custom.UsuarioRepositoryCustom;

public interface UsuarioRepository extends UsuarioRepositoryCustom, JpaRepository<Usuario, Long> {
	abstract <T> Optional<T> findFirstByEmailIgnoreCase(String email, Class<T> type);

	abstract Optional<Usuario> findNivelAcessoById(@Param("id") Long id);

	abstract <T> Optional<T> findFirstById(@Param("id") Long id, Class<T> type);
}
