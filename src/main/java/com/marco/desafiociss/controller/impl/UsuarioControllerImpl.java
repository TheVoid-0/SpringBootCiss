package com.marco.desafiociss.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.marco.desafiociss.controller.UsuarioController;
import com.marco.desafiociss.dto.AutenticacaoUsuarioDTO;
import com.marco.desafiociss.dto.CadastroUsuarioDTO;
import com.marco.desafiociss.dto.LoginUsuarioDTO;
import com.marco.desafiociss.dto.UsuarioDTO;
import com.marco.desafiociss.service.UsuarioService;

@RestController
public class UsuarioControllerImpl implements UsuarioController {

	@Autowired
	@Lazy
	private UsuarioService usuarioService;

	@Override
	public ResponseEntity<Boolean> cadastroUsuario(CadastroUsuarioDTO cadastroUsuarioDTO,
			AutenticacaoUsuarioDTO autenticacaoUsuarioDTO) {
		return ResponseEntity.ok(this.usuarioService.cadastrar(autenticacaoUsuarioDTO, cadastroUsuarioDTO));
	}

	@Override
	public ResponseEntity<AutenticacaoUsuarioDTO> login(LoginUsuarioDTO loginUsuarioDTO) {
		return ResponseEntity.ok(this.usuarioService.login(loginUsuarioDTO));
	}

	@Override
	public ResponseEntity<UsuarioDTO> findOneUsuario(Long id) {
		return ResponseEntity.ok(this.usuarioService.findOneById(id));
	}
}
