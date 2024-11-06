package com.lotte4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBanner is a Querydsl query type for Banner
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBanner extends EntityPathBase<Banner> {

    private static final long serialVersionUID = -713353208L;

    public static final QBanner banner = new QBanner("banner");

    public final StringPath background = createString("background");

    public final NumberPath<Integer> bannerId = createNumber("bannerId", Integer.class);

    public final DatePath<java.sql.Date> eDate = createDate("eDate", java.sql.Date.class);

    public final StringPath eTime = createString("eTime");

    public final StringPath img = createString("img");

    public final StringPath link = createString("link");

    public final StringPath location = createString("location");

    public final StringPath name = createString("name");

    public final DatePath<java.sql.Date> sDate = createDate("sDate", java.sql.Date.class);

    public final StringPath size = createString("size");

    public final NumberPath<Integer> state = createNumber("state", Integer.class);

    public final StringPath sTime = createString("sTime");

    public QBanner(String variable) {
        super(Banner.class, forVariable(variable));
    }

    public QBanner(Path<? extends Banner> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBanner(PathMetadata metadata) {
        super(Banner.class, metadata);
    }

}

