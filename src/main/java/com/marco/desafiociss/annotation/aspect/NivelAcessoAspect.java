package com.marco.desafiociss.annotation.aspect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.marco.desafiociss.annotation.NivelAcesso;
import com.marco.desafiociss.errors.BusinessServerException;
import com.marco.desafiociss.errors.ErrorCode;
import com.marco.desafiociss.errors.ImplementationException;
import com.marco.desafiociss.security.IAutenticacao;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class NivelAcessoAspect {

	@Around("@annotation(com.marco.desafiociss.annotation.NivelAcesso)")
	public <T extends IAutenticacao> Object verifyAccess(ProceedingJoinPoint joinPoint) throws Throwable {
		log.debug("EXECUTANDO ANNOTATION NivelAcesso");

		// Pega a assinatura da junção, realiza um CAST para assinatura de método pois o
		// target dessa annotation é METHOD
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		NivelAcesso nivelAnnotation = method.getAnnotation(NivelAcesso.class);

		if (nivelAnnotation != null) {
			log.debug("anotação NivelAcesso presente");
			// Pega os parâmetros do método anotado
			// Parameter[] params = method.getParameters();
			Long usuarioId = null;
			if (nivelAnnotation.hasUserId()) {

				HashMap<String, Object> paramsNameValue = this.getKeyValueParameters(joinPoint.getArgs(),
						method.getParameters());
				Object id = paramsNameValue.get(nivelAnnotation.usuarioIdField());
				if (!id.getClass().isAssignableFrom(Long.class)) {
					throw new ImplementationException(ErrorCode.NIVEL_ACESSO_ID_INVALIDO);
				}
				usuarioId = (Long) id;
			}

			// Busca nos parâmetros qual é o Objeto que implementa a Classe IAutenticacao
			T autenticacaoDTO = this.getAutenticacaoFromArgs(nivelAnnotation, joinPoint.getArgs());

			if (!autenticacaoDTO.getNivelAcesso().equals(nivelAnnotation.nivelAcessoRequerido())
					&& !autenticacaoDTO.getId().equals(usuarioId)) {
				throw new BusinessServerException(ErrorCode.ACCESS_DENIED);
			}

		}
		return joinPoint.proceed();
	}

	private <T extends IAutenticacao> T getAutenticacaoFromArgs(NivelAcesso nivelAnnotation, Object[] params) {
		for (int i = 0; i < params.length; i++) {

			Class<?>[] interfaces = params[i].getClass().getInterfaces();

			for (int j = 0; j < interfaces.length; j++) {

				if (interfaces[j].equals(IAutenticacao.class)) {
					// Se a Classe passada por parâmetro possuir a implementação IAutenticação então
					// o Cast para T não deve falhar pois está especificado que <T extends
					// IAutenticacao>
					@SuppressWarnings("unchecked")
					T autenticacaoDTO = (T) params[i];
					return autenticacaoDTO;
				}

			}
		}
		// Não deveria chegar aqui
		throw new ImplementationException(ErrorCode.NIVEL_ACESSO_IMPL_ERROR);
	}

	private HashMap<String, Object> getKeyValueParameters(ProceedingJoinPoint joinPoint) {
		HashMap<String, Object> parameters = new HashMap<>();
		Object[] args = joinPoint.getArgs();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Parameter[] params = method.getParameters();

		for (int i = 0; i < params.length; i++) {
			parameters.put(params[i].getName(), args[i]);
		}

		return parameters;
	}

	private HashMap<String, Object> getKeyValueParameters(Object[] args, Parameter[] params) {
		HashMap<String, Object> parameters = new HashMap<>();

		for (int i = 0; i < params.length; i++) {
			parameters.put(params[i].getName(), args[i]);
		}

		return parameters;
	}
}
