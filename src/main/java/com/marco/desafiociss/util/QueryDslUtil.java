package com.marco.desafiociss.util;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;

public final class QueryDslUtil {
	private QueryDslUtil() {
		throw new RuntimeException();
	}

	public static OrderSpecifier<?> getSortedColumn(Order order, String fieldName, Path<?> classe) {
		Path<Object> fieldPath = Expressions.path(Object.class, classe, fieldName);
		return new OrderSpecifier(order, fieldPath);
	}

	public static Order getOrderValueOrNull(String str) {
		try {
			return Order.valueOf(str);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	/**
	 * Adiciona os % % da operação LIKE
	 * 
	 * @return
	 */
	public static String addLike(String str) {
		return String.join("%", str, "%");
	}
}
