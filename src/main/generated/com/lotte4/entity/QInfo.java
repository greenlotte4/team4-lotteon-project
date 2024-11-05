package com.lotte4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInfo is a Querydsl query type for Info
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInfo extends EntityPathBase<Info> {

    private static final long serialVersionUID = 535790730L;

    public static final QInfo info = new QInfo("info");

    public final StringPath companyAddress = createString("companyAddress");

    public final StringPath companyAddress2 = createString("companyAddress2");

    public final StringPath companyBusinessNumber = createString("companyBusinessNumber");

    public final StringPath companyCeo = createString("companyCeo");

    public final StringPath companyName = createString("companyName");

    public final StringPath consumer = createString("consumer");

    public final StringPath copyright = createString("copyright");

    public final StringPath csEmail = createString("csEmail");

    public final StringPath csHp = createString("csHp");

    public final StringPath csWorkingHours = createString("csWorkingHours");

    public final StringPath favicon = createString("favicon");

    public final StringPath footerLogo = createString("footerLogo");

    public final StringPath headerLogo = createString("headerLogo");

    public final NumberPath<Integer> infoId = createNumber("infoId", Integer.class);

    public final StringPath mosaNumber = createString("mosaNumber");

    public final StringPath subTitle = createString("subTitle");

    public final StringPath title = createString("title");

    public QInfo(String variable) {
        super(Info.class, forVariable(variable));
    }

    public QInfo(Path<? extends Info> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInfo(PathMetadata metadata) {
        super(Info.class, metadata);
    }

}

