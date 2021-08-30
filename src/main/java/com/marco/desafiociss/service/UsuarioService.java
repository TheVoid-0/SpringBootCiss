package com.marco.desafiociss.service;

import com.marco.desafiociss.dto.AutenticacaoUsuarioDTO;
import com.marco.desafiociss.dto.CadastroUsuarioDTO;
import com.marco.desafiociss.dto.LoginUsuarioDTO;
import com.marco.desafiociss.dto.UsuarioDTO;
import com.marco.desafiociss.enums.NivelAcessoEnum;

public interface UsuarioService {
	abstract Boolean cadastrar(AutenticacaoUsuarioDTO autenticacaoDTO, CadastroUsuarioDTO cadastroUsuarioDTO);

	abstract AutenticacaoUsuarioDTO login(LoginUsuarioDTO loginUsuarioDTO);

	abstract NivelAcessoEnum getNivelAcesso(Long id);

	abstract UsuarioDTO findOneById(Long id);
}
