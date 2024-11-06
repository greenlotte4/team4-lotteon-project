package com.lotte4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCoupon is a Querydsl query type for Coupon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoupon extends EntityPathBase<Coupon> {

    private static final long serialVersionUID = -671583998L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCoupon coupon = new QCoupon("coupon");

    public final NumberPath<Integer> benefit = createNumber("benefit", Integer.class);

    public final NumberPath<Integer> couponId = createNumber("couponId", Integer.class);

    public final NumberPath<Integer> dDate = createNumber("dDate", Integer.class);

    public final StringPath ect = createString("ect");

    public final DatePath<java.sql.Date> eDate = createDate("eDate", java.sql.Date.class);

    public final DateTimePath<java.time.LocalDateTime> IDate = createDateTime("IDate", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final QOrder order;

    public final DatePath<java.sql.Date> sDate = createDate("sDate", java.sql.Date.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Integer> totalIssued = createNumber("totalIssued", Integer.class);

    public final NumberPath<Integer> totalUsed = createNumber("totalUsed", Integer.class);

    public final StringPath type = createString("type");

    public final QUser users;

    public QCoupon(String variable) {
        this(Coupon.class, forVariable(variable), INITS);
    }

    public QCoupon(Path<? extends Coupon> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCoupon(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCoupon(PathMetadata metadata, PathInits inits) {
        this(Coupon.class, metadata, inits);
    }

    public QCoupon(Class<? extends Coupon> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new QOrder(forProperty("order"), inits.get("order")) : null;
        this.users = inits.isInitialized("users") ? new QUser(forProperty("users"), inits.get("users")) : null;
    }

}

