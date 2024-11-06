package com.lotte4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -564698382L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final DateTimePath<java.time.LocalDateTime> buyDate = createDateTime("buyDate", java.time.LocalDateTime.class);

    public final QCoupon coupon;

    public final NumberPath<Integer> couponUse = createNumber("couponUse", Integer.class);

    public final QMemberInfo memberInfo;

    public final NumberPath<Integer> orderId = createNumber("orderId", Integer.class);

    public final ListPath<OrderItems, QOrderItems> orderItems = this.<OrderItems, QOrderItems>createList("orderItems", OrderItems.class, QOrderItems.class, PathInits.DIRECT2);

    public final NumberPath<Integer> Payment = createNumber("Payment", Integer.class);

    public final QProductVariants productVariants;

    public final StringPath recipAddr1 = createString("recipAddr1");

    public final StringPath recipAddr2 = createString("recipAddr2");

    public final StringPath recipHp = createString("recipHp");

    public final StringPath recipName = createString("recipName");

    public final StringPath recipZip = createString("recipZip");

    public final NumberPath<Integer> Status = createNumber("Status", Integer.class);

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public final NumberPath<Integer> usePoint = createNumber("usePoint", Integer.class);

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.coupon = inits.isInitialized("coupon") ? new QCoupon(forProperty("coupon"), inits.get("coupon")) : null;
        this.memberInfo = inits.isInitialized("memberInfo") ? new QMemberInfo(forProperty("memberInfo"), inits.get("memberInfo")) : null;
        this.productVariants = inits.isInitialized("productVariants") ? new QProductVariants(forProperty("productVariants"), inits.get("productVariants")) : null;
    }

}

