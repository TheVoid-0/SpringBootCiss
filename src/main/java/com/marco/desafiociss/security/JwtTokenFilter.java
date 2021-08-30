package com.marco.desafiociss.security;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.marco.desafiociss.dto.AutenticacaoUsuarioDTO;

public class JwtTokenFilter extends OncePerRequestFilter {

	// private final static String ROLE_PREFIX = "ROLE_";
	private JwtTokenProvider jwtTokenProvider;

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		AutenticacaoUsuarioDTO autenticacaoUsuarioDTO = this.jwtTokenProvider.resolveToken(request,
				AutenticacaoUsuarioDTO.class);

		if (autenticacaoUsuarioDTO != null) {
			// Poderia adicionar uma Role aqui e blindar os endpoints solicitando aquela
			// role pelo WebSecurityConfig ou com @PreAuthorize, mas isso será delegado para
			// a implementação de cada Service
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					autenticacaoUsuarioDTO, null, Arrays.asList(autenticacaoUsuarioDTO.getNivelAcesso()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

}
