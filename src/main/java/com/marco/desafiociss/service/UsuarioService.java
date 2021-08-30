package com.marco.desafiociss.service;

import com.marco.desafiociss.dto.AutenticacaoUsuarioDTO;
import com.marco.desafiociss.dto.CadastroUsuarioDTO;
import com.marco.desafiociss.dto.EditarUsuarioDTO;
import com.marco.desafiociss.dto.FiltroUsuarioDTO;
import com.marco.desafiociss.dto.LoginUsuarioDTO;
import com.marco.desafiociss.dto.PageDTO;
import com.marco.desafiociss.dto.UsuarioDTO;
import com.marco.desafiociss.enums.NivelAcessoEnum;
import com.marco.desafiociss.security.IAutenticacao;

public interface UsuarioService {
	abstract Boolean cadastrar(AutenticacaoUsuarioDTO autenticacaoDTO, CadastroUsuarioDTO cadastroUsuarioDTO);

	abstract AutenticacaoUsuarioDTO login(LoginUsuarioDTO loginUsuarioDTO);

	abstract NivelAcessoEnum getNivelAcesso(Long id);

	abstract UsuarioDTO findOneById(AutenticacaoUsuarioDTO autenticacaoUsuarioDTO, Long id);

	abstract PageDTO<UsuarioDTO> filtrarUsuarios(FiltroUsuarioDTO filtroUsuarioDTO);

	abstract UsuarioDTO save(AutenticacaoUsuarioDTO autenticacaoUsuarioDTO, Long id, EditarUsuarioDTO editarUsuarioDTO);

	abstract void delete(AutenticacaoUsuarioDTO autenticacaoUsuarioDTO, Long id);
}
