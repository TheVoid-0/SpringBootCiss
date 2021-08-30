package com.marco.desafiociss.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

import com.marco.desafiociss.controller.UsuarioController;
import com.marco.desafiociss.dto.AutenticacaoUsuarioDTO;
import com.marco.desafiociss.dto.CadastroUsuarioDTO;
import com.marco.desafiociss.dto.EditarUsuarioDTO;
import com.marco.desafiociss.dto.FiltroUsuarioDTO;
import com.marco.desafiociss.dto.LoginUsuarioDTO;
import com.marco.desafiociss.dto.PageDTO;
import com.marco.desafiociss.dto.UsuarioDTO;
import com.marco.desafiociss.dto.UsuarioProjectionDTO;
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
	public ResponseEntity<UsuarioProjectionDTO> findOneUsuario(
			@AuthenticationPrincipal AutenticacaoUsuarioDTO autenticacaoUsuarioDTO, Long id) {
		return ResponseEntity.ok(this.usuarioService.findOneById(autenticacaoUsuarioDTO, id));
	}

	@Override
	public ResponseEntity<PageDTO<UsuarioDTO>> filtrarUsuarios(FiltroUsuarioDTO filtroUsuarioDTO) {
		return ResponseEntity.ok(this.usuarioService.filtrarUsuarios(filtroUsuarioDTO));
	}

	@Override
	public ResponseEntity<?> delete(AutenticacaoUsuarioDTO autenticacaoUsuarioDTO, Long id) {
		this.usuarioService.delete(autenticacaoUsuarioDTO, id);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<UsuarioDTO> editarUsuario(Long id, EditarUsuarioDTO editarUsuarioDTO,
			AutenticacaoUsuarioDTO autenticacaoUsuarioDTO) {
		return ResponseEntity.ok(this.usuarioService.save(autenticacaoUsuarioDTO, id, editarUsuarioDTO));
	}
}
