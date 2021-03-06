package com.marco.desafiociss.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marco.desafiociss.dto.AutenticacaoUsuarioDTO;
import com.marco.desafiociss.enums.NivelAcessoEnum;
import com.marco.desafiociss.errors.BusinessServerException;
import com.marco.desafiociss.errors.ErrorCode;
import com.marco.desafiociss.service.UsuarioService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {
	private static final String AUTHORIZATION = "Authorization";

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.expires_in_miliseconds}")
	private long EXPIRES_IN_MILLISECONDS; // 24H

	@Autowired
	@Lazy
	private ObjectMapper jsonMapper;

	@Autowired
	@Lazy
	private ModelMapper modelMapper;

	@Autowired
	@Lazy
	private UsuarioService usuarioService;

	public <T, S> String generateToken(T payload, Class<S> mappedType) {
		log.debug("EXECUTANDO METODO generateToken");

		S autenticacaoUsuarioDTO = modelMapper.map(payload, mappedType);

		try {
			Claims claims = Jwts.claims().setSubject(jsonMapper.writeValueAsString(autenticacaoUsuarioDTO));
			Date now = new Date();
			Date expirationDate = new Date(now.getTime() + EXPIRES_IN_MILLISECONDS);

			return Jwts.builder()//
					.setClaims(claims)//
					.setIssuedAt(now)//
					.setExpiration(expirationDate)//
					.signWith(SignatureAlgorithm.HS256, jwtSecret)//
					.compact();
		} catch (JsonProcessingException e) {
			throw new BusinessServerException(ErrorCode.ERROR_CREATION_JWT);
		}
	}

	public <S extends IAutenticacao> String generateToken(S payload) {
		log.debug("EXECUTANDO METODO generateToken");

		try {
			Claims claims = Jwts.claims().setSubject(jsonMapper.writeValueAsString(payload));
			Date now = new Date();
			Date expirationDate = new Date(now.getTime() + EXPIRES_IN_MILLISECONDS);

			return Jwts.builder()//
					.setClaims(claims)//
					.setIssuedAt(now)//
					.setExpiration(expirationDate)//
					.signWith(SignatureAlgorithm.HS256, jwtSecret)//
					.compact();
		} catch (JsonProcessingException e) {
			throw new BusinessServerException(ErrorCode.ERROR_CREATION_JWT);
		}
	}

	public String getToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION);

		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}

		if (bearerToken != null) {
			return bearerToken;
		}
		return null;
	}

	/**
	 * Valida o token se estiver presente
	 *
	 * @param type classe a qual o objeto de autenticacao ser?? mapeado.<br>
	 *             Essa classe deve obrigatoriamente implementar
	 *             {@link IAutenticacao}
	 *
	 * @return Inst??ncia do AutenticacaoUsuarioDTO com o nivel de acesso atualizado
	 *         se o token for v??lido, caso contr??rio, {@code null}
	 */
	public <T extends IAutenticacao> T resolveToken(HttpServletRequest request, Class<T> type) {
		log.debug("EXECUTANDO METODO resolveToken");

		String bearerToken = request.getHeader(AUTHORIZATION);
		T autenticacaoDTO = null;

		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {

			bearerToken = bearerToken.substring(7, bearerToken.length());
			autenticacaoDTO = this.tryGetInfo(bearerToken, type);

		} else if (bearerToken != null) {
			this.tryGetInfo(bearerToken, AutenticacaoUsuarioDTO.class);
		}
		// Busca permiss??o no banco mesmo possuindo nas informa????es do token por
		// quest??es de seguran??a
		if (autenticacaoDTO != null) {
			autenticacaoDTO.setNivelAcesso(this.usuarioService.getNivelAcesso(autenticacaoDTO.getId()));
			autenticacaoDTO.setJwtToken(bearerToken);
		}
		return autenticacaoDTO;
	}

	/**
	 * Tenta mapear as informa????es contidas no token JWT para a classe {@code T}
	 * passada como par??metro.
	 * 
	 * Esse c??digo pode n??o funcionar como o esperado se a classe e o conte??do do
	 * JWT forem incompat??veis
	 * 
	 * @param <T>
	 * @param token String contendo o token JWT
	 * @param type  classe para qual as claims do JWT ser??o mapeadas
	 * @return Uma inst??ncia da classe {@code T} passada como par??metro
	 */
	public <T extends IAutenticacao> T tryGetInfo(String token, Class<T> type) {
		log.debug("EXECUTANDO METODO tryGetInfo");
		try {

			Jws<Claims> claims = Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(token);
			return this.jsonMapper.readValue(claims.getBody().getSubject(), type);

		} catch (JsonProcessingException e) {
			return null;
		}
	}

	/**
	 * Verifica se o usuario logado possui acesso a um recurso espec??fico, a l??gica
	 * pode ser modificada se mais n??veis de recursos forem necess??rios.<br>
	 * Deve ser usado quando a falha na autentica????o impede o sistema de continuar
	 */
	public void verifyAccess(IAutenticacao autenticacaoDTO, NivelAcessoEnum nivelRequerido, Long idUsuario) {
		if (!autenticacaoDTO.getNivelAcesso().equals(nivelRequerido) && !autenticacaoDTO.getId().equals(idUsuario)) {
			throw new BusinessServerException(ErrorCode.ACCESS_DENIED);
		}
	}

	/**
	 * Verifica se o usuario logado possui acesso administrativo.
	 */
	public Boolean isAdmin(IAutenticacao autenticacaoDTO) {
		if (!autenticacaoDTO.getNivelAcesso().equals(NivelAcessoEnum.ADMIN)) {
			return false;
		}
		return true;
	}

	public Boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
