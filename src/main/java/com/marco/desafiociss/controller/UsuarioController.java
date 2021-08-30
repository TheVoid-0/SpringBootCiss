package com.marco.desafiociss.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marco.desafiociss.dto.AutenticacaoUsuarioDTO;
import com.marco.desafiociss.dto.CadastroUsuarioDTO;
import com.marco.desafiociss.dto.LoginUsuarioDTO;

@RestController
@RequestMapping("/usuario")
public interface UsuarioController {

	@PostMapping()
	abstract ResponseEntity<?> cadastroUsuario(
			@Valid @RequestBody(required = true) CadastroUsuarioDTO cadastroUsuarioDTO,
			@AuthenticationPrincipal AutenticacaoUsuarioDTO autenticacaoUsuarioDTO);

	@PostMapping("/auth")
	abstract ResponseEntity<?> login(@Valid @RequestBody LoginUsuarioDTO loginUsuarioDTO);

	@GetMapping("/{id}")
	abstract ResponseEntity<?> findOneUsuario(@PathVariable("id") Long id);
}
