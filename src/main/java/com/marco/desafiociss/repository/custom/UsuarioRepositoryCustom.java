package com.marco.desafiociss.repository.custom;

import com.marco.desafiociss.dto.FiltroUsuarioDTO;
import com.marco.desafiociss.dto.PageDTO;

public interface UsuarioRepositoryCustom {

	abstract <T> PageDTO<T> filtrarUsuarios(FiltroUsuarioDTO filtroUsuarioDTO, Class<T> projectionType);
}
