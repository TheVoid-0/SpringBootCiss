package com.marco.desafiociss.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.marco.desafiociss.enums.NivelAcessoEnum;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NivelAcesso {

	NivelAcessoEnum nivelAcessoRequerido() default NivelAcessoEnum.ADMIN;

}
