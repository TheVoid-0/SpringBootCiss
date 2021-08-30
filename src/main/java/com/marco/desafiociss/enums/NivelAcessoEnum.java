package com.marco.desafiociss.enums;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;

public enum NivelAcessoEnum implements GrantedAuthority {
	NORMAL(0L), ADMIN(1L);

	private Long identificador;

	private static final Map<Long, NivelAcessoEnum> TYPES_BY_VALUE = new HashMap<>();

	static {
		for (NivelAcessoEnum type : NivelAcessoEnum.values()) {
			TYPES_BY_VALUE.put(type.identificador, type);
		}
	}

	NivelAcessoEnum(Long identificador) {
		this.identificador = identificador;
	}

	public Long getValue() {
		return this.identificador;
	}

	public static NivelAcessoEnum forValue(Long value) {
		return TYPES_BY_VALUE.get(value);
	}

	@Override
	public String getAuthority() {
		return NivelAcessoEnum.forValue(this.identificador).toString();
	}
}
