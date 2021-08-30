package com.marco.desafiociss.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsuario is a Querydsl query type for Usuario
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsuario extends EntityPathBase<Usuario> {

    private static final long serialVersionUID = -1642406848L;

    public static final QUsuario usuario = new QUsuario("usuario");

    public final DateTimePath<java.util.Date> dataAtualizacao = createDateTime("dataAtualizacao", java.util.Date.class);

    public final DateTimePath<java.util.Date> dataCadastro = createDateTime("dataCadastro", java.util.Date.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.marco.desafiociss.enums.NivelAcessoEnum> nivelAcesso = createEnum("nivelAcesso", com.marco.desafiociss.enums.NivelAcessoEnum.class);

    public final StringPath nome = createString("nome");

    public final StringPath pis = createString("pis");

    public final StringPath senha = createString("senha");

    public final StringPath sobrenome = createString("sobrenome");

    public QUsuario(String variable) {
        super(Usuario.class, forVariable(variable));
    }

    public QUsuario(Path<? extends Usuario> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsuario(PathMetadata metadata) {
        super(Usuario.class, metadata);
    }

}

