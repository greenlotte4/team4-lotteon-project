package com.lotte4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTerms is a Querydsl query type for Terms
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTerms extends EntityPathBase<Terms> {

    private static final long serialVersionUID = -560454357L;

    public static final QTerms terms = new QTerms("terms");

    public final StringPath finance = createString("finance");

    public final StringPath location = createString("location");

    public final StringPath privacy = createString("privacy");

    public final StringPath tax = createString("tax");

    public final StringPath term = createString("term");

    public final NumberPath<Integer> termsId = createNumber("termsId", Integer.class);

    public QTerms(String variable) {
        super(Terms.class, forVariable(variable));
    }

    public QTerms(Path<? extends Terms> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTerms(PathMetadata metadata) {
        super(Terms.class, metadata);
    }

}

