package com.marco.desafiociss.util;

import com.marco.desafiociss.errors.BusinessServerException;
import com.marco.desafiociss.errors.ErrorCode;

public final class ValidacaoUtil {
	private ValidacaoUtil() {
		throw new RuntimeException();
	}

	public static String validarPis(String pis) {
		String rippedPis = pis.trim().replace("-", "").replace(".", "");
		if (rippedPis.length() < 11) {
			throw new BusinessServerException(ErrorCode.INVALID_PIS);
		}
		char[] pisChars = rippedPis.toCharArray();
		int digitoVerificador = Character.getNumericValue(pisChars[10]);
		int[] pesos = { 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
		int totalSoma = 0;

		for (int i = 0; i < 10; i++) {
			totalSoma += pesos[i] * Character.getNumericValue(pisChars[i]);
		}
		int resto = totalSoma % 11;
		int result = 11 - resto;

		if (resto < 2)
			resto = 0;
		else
			resto = 11 - resto;

		if (result != digitoVerificador) {
			throw new BusinessServerException(ErrorCode.INVALID_PIS);
		}

		return rippedPis;

	}
}
