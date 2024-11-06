package com.lotte4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderItems is a Querydsl query type for OrderItems
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderItems extends EntityPathBase<OrderItems> {

    private static final long serialVersionUID = 1545968622L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderItems orderItems = new QOrderItems("orderItems");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final QDelivery delivery;

    public final NumberPath<Integer> deliveryFee = createNumber("deliveryFee", Integer.class);

    public final StringPath itemOption = createString("itemOption");

    public final QOrder order;

    public final NumberPath<Integer> orderItemId = createNumber("orderItemId", Integer.class);

    public final NumberPath<Integer> originDiscount = createNumber("originDiscount", Integer.class);

    public final NumberPath<Integer> originPoint = createNumber("originPoint", Integer.class);

    public final NumberPath<Integer> originPrice = createNumber("originPrice", Integer.class);

    public final NumberPath<Integer> variantId = createNumber("variantId", Integer.class);

    public QOrderItems(String variable) {
        this(OrderItems.class, forVariable(variable), INITS);
    }

    public QOrderItems(Path<? extends OrderItems> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderItems(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderItems(PathMetadata metadata, PathInits inits) {
        this(OrderItems.class, metadata, inits);
    }

    public QOrderItems(Class<? extends OrderItems> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.delivery = inits.isInitialized("delivery") ? new QDelivery(forProperty("delivery"), inits.get("delivery")) : null;
        this.order = inits.isInitialized("order") ? new QOrder(forProperty("order"), inits.get("order")) : null;
    }

}

