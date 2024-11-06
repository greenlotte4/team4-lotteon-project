package com.lotte4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSellerInfo is a Querydsl query type for SellerInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSellerInfo extends EntityPathBase<SellerInfo> {

    private static final long serialVersionUID = 1126570921L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSellerInfo sellerInfo = new QSellerInfo("sellerInfo");

    public final QAddress address;

    public final StringPath bizNumber = createString("bizNumber");

    public final StringPath ceo = createString("ceo");

    public final StringPath comName = createString("comName");

    public final StringPath comNumber = createString("comNumber");

    public final StringPath email = createString("email");

    public final StringPath fax = createString("fax");

    public final StringPath hp = createString("hp");

    public final StringPath regIp = createString("regIp");

    public final NumberPath<Integer> sellerInfoId = createNumber("sellerInfoId", Integer.class);

    public final NumberPath<Integer> state = createNumber("state", Integer.class);

    public final StringPath updateAt = createString("updateAt");

    public QSellerInfo(String variable) {
        this(SellerInfo.class, forVariable(variable), INITS);
    }

    public QSellerInfo(Path<? extends SellerInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSellerInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSellerInfo(PathMetadata metadata, PathInits inits) {
        this(SellerInfo.class, metadata, inits);
    }

    public QSellerInfo(Class<? extends SellerInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new QAddress(forProperty("address")) : null;
    }

}

