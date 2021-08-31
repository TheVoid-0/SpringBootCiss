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
	 * @param type classe a qual o objeto de autenticacao será mapeado.<br>
	 *             Essa classe deve obrigatoriamente implementar
	 *             {@link IAutenticacao}
	 *
	 * @return Instância do AutenticacaoUsuarioDTO com o nivel de acesso atualizado
	 *         se o token for válido, caso contrário, {@code null}
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
		// Busca permissão no banco mesmo possuindo nas informações do token por
		// questões de segurança
		if (autenticacaoDTO != null) {
			autenticacaoDTO.setNivelAcesso(this.usuarioService.getNivelAcesso(autenticacaoDTO.getId()));
			autenticacaoDTO.setJwtToken(bearerToken);
		}
		return autenticacaoDTO;
	}

	/**
	 * Tenta mapear as informações contidas no token JWT para a classe {@code T}
	 * passada como parâmetro.
	 * 
	 * Esse código pode não funcionar como o esperado se a classe e o conteúdo do
	 * JWT forem incompatíveis
	 * 
	 * @param <T>
	 * @param token String contendo o token JWT
	 * @param type  classe para qual as claims do JWT serão mapeadas
	 * @return Uma instância da classe {@code T} passada como parâmetro
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
	 * Verifica se o usuario logado possui acesso a um recurso específico, a lógica
	 * pode ser modificada se mais níveis de recursos forem necessários.<br>
	 * Deve ser usado quando a falha na autenticação impede o sistema de continuar
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
