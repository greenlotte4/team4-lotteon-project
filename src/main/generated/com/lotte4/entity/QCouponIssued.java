package com.lotte4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCouponIssued is a Querydsl query type for CouponIssued
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCouponIssued extends EntityPathBase<CouponIssued> {

    private static final long serialVersionUID = -1962375603L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCouponIssued couponIssued = new QCouponIssued("couponIssued");

    public final QCoupon coupon;

    public final NumberPath<Integer> IssueId = createNumber("IssueId", Integer.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final DatePath<java.sql.Date> uDate = createDate("uDate", java.sql.Date.class);

    public final QUser users;

    public QCouponIssued(String variable) {
        this(CouponIssued.class, forVariable(variable), INITS);
    }

    public QCouponIssued(Path<? extends CouponIssued> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCouponIssued(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCouponIssued(PathMetadata metadata, PathInits inits) {
        this(CouponIssued.class, metadata, inits);
    }

    public QCouponIssued(Class<? extends CouponIssued> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.coupon = inits.isInitialized("coupon") ? new QCoupon(forProperty("coupon"), inits.get("coupon")) : null;
        this.users = inits.isInitialized("users") ? new QUser(forProperty("users"), inits.get("users")) : null;
    }

}

