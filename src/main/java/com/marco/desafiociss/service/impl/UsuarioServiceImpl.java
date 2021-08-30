package com.marco.desafiociss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.marco.desafiociss.annotation.NivelAcesso;
import com.marco.desafiociss.domain.Usuario;
import com.marco.desafiociss.dto.AutenticacaoUsuarioDTO;
import com.marco.desafiociss.dto.CadastroUsuarioDTO;
import com.marco.desafiociss.dto.EditarUsuarioDTO;
import com.marco.desafiociss.dto.FiltroUsuarioDTO;
import com.marco.desafiociss.dto.LoginUsuarioDTO;
import com.marco.desafiociss.dto.PageDTO;
import com.marco.desafiociss.dto.UsuarioDTO;
import com.marco.desafiociss.enums.NivelAcessoEnum;
import com.marco.desafiociss.errors.BusinessServerException;
import com.marco.desafiociss.errors.ErrorCode;
import com.marco.desafiociss.repository.UsuarioRepository;
import com.marco.desafiociss.security.IAutenticacao;
import com.marco.desafiociss.security.JwtTokenProvider;
import com.marco.desafiociss.service.UsuarioService;

import org.modelmapper.ModelMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	@Lazy
	private UsuarioRepository usuarioRepository;

	@Autowired
	@Lazy
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	@Lazy
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	@Lazy
	private ModelMapper modelMapper;

	@Override
	@NivelAcesso(nivelAcessoRequerido = NivelAcessoEnum.ADMIN)
	public Boolean cadastrar(AutenticacaoUsuarioDTO autenticacaoDTO, CadastroUsuarioDTO cadastroUsuarioDTO) {
		log.info("executando metodo cadastrar");

		if (this.usuarioRepository.findFirstByEmailIgnoreCase(cadastroUsuarioDTO.getEmail(), Long.class).isPresent()) {
			throw new BusinessServerException(ErrorCode.USER_ALREADY_EXISTS);
		}

		Usuario usuario = this.modelMapper.map(cadastroUsuarioDTO, Usuario.class);
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		this.usuarioRepository.save(usuario);
		return true;
	}

	@Override
	public AutenticacaoUsuarioDTO login(LoginUsuarioDTO loginUsuarioDTO) {
		log.info("executando metodo login");

		Usuario usuario = this.usuarioRepository.findFirstByEmailIgnoreCase(loginUsuarioDTO.getEmail(), Usuario.class)
				.orElseThrow(() -> new BusinessServerException(ErrorCode.USER_NOT_FOUND));

		if (!passwordEncoder.matches(loginUsuarioDTO.getSenha(), usuario.getSenha())) {
			throw new BusinessServerException(ErrorCode.INVALID_PASSWORD);
		}

		AutenticacaoUsuarioDTO autenticacaoUsuarioDTO = this.modelMapper.map(usuario, AutenticacaoUsuarioDTO.class);

		autenticacaoUsuarioDTO.setJwtToken(this.jwtTokenProvider.generateToken(autenticacaoUsuarioDTO));

		return autenticacaoUsuarioDTO;

	}

	@Override
	public NivelAcessoEnum getNivelAcesso(Long id) {
		return this.usuarioRepository.findNivelAcessoById(id)
				.orElseThrow(() -> new BusinessServerException(ErrorCode.USER_NOT_FOUND)).getNivelAcesso();
	}

	@Override
	public UsuarioDTO findOneById(AutenticacaoUsuarioDTO autenticacaoUsuarioDTO, Long id) {
		this.jwtTokenProvider.verifyAccess(autenticacaoUsuarioDTO, NivelAcessoEnum.ADMIN, id);
		return this.usuarioRepository.findFirstById(id, UsuarioDTO.class)
				.orElseThrow(() -> new BusinessServerException(ErrorCode.USER_NOT_FOUND));
	}

	@Override
	public PageDTO<UsuarioDTO> filtrarUsuarios(FiltroUsuarioDTO filtroUsuarioDTO) {
		return this.usuarioRepository.filtrarUsuarios(filtroUsuarioDTO, UsuarioDTO.class);
	}

	@Override
	@NivelAcesso(nivelAcessoRequerido = NivelAcessoEnum.ADMIN)
	public void delete(AutenticacaoUsuarioDTO autenticacaoUsuarioDTO, Long id) {
		this.usuarioRepository.deleteById(id);
	}

	@Override
	public UsuarioDTO save(AutenticacaoUsuarioDTO autenticacaoUsuarioDTO, Long id, EditarUsuarioDTO editarUsuarioDTO) {
		this.jwtTokenProvider.verifyAccess(autenticacaoUsuarioDTO, NivelAcessoEnum.ADMIN, id);

		Usuario usuario = this.usuarioRepository.findById(id)
				.orElseThrow(() -> new BusinessServerException(ErrorCode.USER_NOT_FOUND));
		usuario = this.modelMapper.map(editarUsuarioDTO, Usuario.class);

		return this.modelMapper.map(this.usuarioRepository.save(usuario), UsuarioDTO.class);

	}
}
