package com.lotte4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductCate is a Querydsl query type for ProductCate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductCate extends EntityPathBase<ProductCate> {

    private static final long serialVersionUID = 2139307394L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductCate productCate = new QProductCate("productCate");

    public final ListPath<ProductCate, QProductCate> children = this.<ProductCate, QProductCate>createList("children", ProductCate.class, QProductCate.class, PathInits.DIRECT2);

    public final NumberPath<Integer> depth = createNumber("depth", Integer.class);

    public final StringPath name = createString("name");

    public final QProductCate parent;

    public final NumberPath<Integer> productCateId = createNumber("productCateId", Integer.class);

    public QProductCate(String variable) {
        this(ProductCate.class, forVariable(variable), INITS);
    }

    public QProductCate(Path<? extends ProductCate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductCate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductCate(PathMetadata metadata, PathInits inits) {
        this(ProductCate.class, metadata, inits);
    }

    public QProductCate(Class<? extends ProductCate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parent = inits.isInitialized("parent") ? new QProductCate(forProperty("parent"), inits.get("parent")) : null;
    }

}

