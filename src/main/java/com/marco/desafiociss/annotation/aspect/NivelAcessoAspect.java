package com.marco.desafiociss.annotation.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.marco.desafiociss.annotation.NivelAcesso;
import com.marco.desafiociss.errors.BusinessServerException;
import com.marco.desafiociss.errors.ErrorCode;
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

			// Pega os parâmetros do método anotado
			Object[] params = joinPoint.getArgs();
			boolean foundAuthObject = false;
			// Busca nos parâmetros qual é o Objeto que implementa a Classe IAutenticacao
			for (int i = 0; i < params.length && !foundAuthObject; i++) {

				Class<?>[] interfaces = params[i].getClass().getInterfaces();

				for (int j = 0; j < interfaces.length; j++) {

					if (interfaces[j].equals(IAutenticacao.class)) {
						@SuppressWarnings("unchecked")
						T autenticacaoDTO = (T) params[i];
						foundAuthObject = true;
						// Checa se a autenticacao é suficiente para acessar o método anotado
						if (!autenticacaoDTO.getNivelAcesso().equals(nivelAnnotation.nivelAcessoRequerido())) {
							throw new BusinessServerException(ErrorCode.ACCESS_DENIED);
						}
						break;
					}

				}
			}
			if (foundAuthObject = false) {
				// Não deveria chegar aqui
				throw new BusinessServerException(ErrorCode.IMPLEMENTATION_ERROR);
			}
		}
		return joinPoint.proceed();
	}
}
