package com.lotte4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberInfo is a Querydsl query type for MemberInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberInfo extends EntityPathBase<MemberInfo> {

    private static final long serialVersionUID = -531607228L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberInfo memberInfo = new QMemberInfo("memberInfo");

    public final QAddress address;

    public final StringPath email = createString("email");

    public final StringPath etc = createString("etc");

    public final NumberPath<Integer> gender = createNumber("gender", Integer.class);

    public final StringPath grade = createString("grade");

    public final StringPath hp = createString("hp");

    public final StringPath lastLoginAt = createString("lastLoginAt");

    public final NumberPath<Integer> memberInfoId = createNumber("memberInfoId", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final StringPath status = createString("status");

    public final StringPath updatedAt = createString("updatedAt");

    public QMemberInfo(String variable) {
        this(MemberInfo.class, forVariable(variable), INITS);
    }

    public QMemberInfo(Path<? extends MemberInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberInfo(PathMetadata metadata, PathInits inits) {
        this(MemberInfo.class, metadata, inits);
    }

    public QMemberInfo(Class<? extends MemberInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new QAddress(forProperty("address")) : null;
    }

}

