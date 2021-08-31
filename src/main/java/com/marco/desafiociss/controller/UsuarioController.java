package com.marco.desafiociss.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marco.desafiociss.dto.AutenticacaoUsuarioDTO;
import com.marco.desafiociss.dto.CadastroUsuarioDTO;
import com.marco.desafiociss.dto.EditarUsuarioDTO;
import com.marco.desafiociss.dto.FiltroUsuarioDTO;
import com.marco.desafiociss.dto.LoginUsuarioDTO;

@RestController
@RequestMapping("/usuarios")
public interface UsuarioController {

	@PostMapping()
	abstract ResponseEntity<?> cadastroUsuario(
			@Valid @RequestBody(required = true) CadastroUsuarioDTO cadastroUsuarioDTO,
			@AuthenticationPrincipal AutenticacaoUsuarioDTO autenticacaoUsuarioDTO);

	@PostMapping("/auth")
	abstract ResponseEntity<?> login(@Valid @RequestBody LoginUsuarioDTO loginUsuarioDTO);

	@GetMapping("/{id}")
	abstract ResponseEntity<?> findOneUsuario(@AuthenticationPrincipal AutenticacaoUsuarioDTO autenticacaoUsuarioDTO,
			@PathVariable("id") Long id);

	@GetMapping()
	abstract ResponseEntity<?> filtrarUsuarios(@Valid FiltroUsuarioDTO filtroUsuarioDTO);

	@DeleteMapping("/{id}")
	abstract ResponseEntity<?> delete(@AuthenticationPrincipal AutenticacaoUsuarioDTO autenticacaoUsuarioDTO,
			@PathVariable("id") Long id);

	@PutMapping("/{id}")
	abstract ResponseEntity<?> editarUsuario(@PathVariable("id") Long id,
			@Valid @RequestBody(required = true) EditarUsuarioDTO editarUsuarioDTO,
			@AuthenticationPrincipal AutenticacaoUsuarioDTO autenticacaoUsuarioDTO);
}
