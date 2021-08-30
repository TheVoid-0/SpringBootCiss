package com.marco.desafiociss.dto;

import java.util.List;

import lombok.Data;

@Data
public class PageDTO<T> {
	private Long totalCount;
	private Long page;
	private List<T> content;
}
