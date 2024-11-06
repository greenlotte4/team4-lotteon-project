package com.lotte4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductVariants is a Querydsl query type for ProductVariants
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductVariants extends EntityPathBase<ProductVariants> {

    private static final long serialVersionUID = 460812609L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductVariants productVariants = new QProductVariants("productVariants");

    public final MapPath<java.util.List<String>, java.util.List<String>, SimplePath<java.util.List<String>>> options = this.<java.util.List<String>, java.util.List<String>, SimplePath<java.util.List<String>>>createMap("options", java.util.List.class, java.util.List.class, SimplePath.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final QProduct product;

    public final StringPath sku = createString("sku");

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> updated_at = createDateTime("updated_at", java.time.LocalDateTime.class);

    public final NumberPath<Integer> variant_id = createNumber("variant_id", Integer.class);

    public QProductVariants(String variable) {
        this(ProductVariants.class, forVariable(variable), INITS);
    }

    public QProductVariants(Path<? extends ProductVariants> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductVariants(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductVariants(PathMetadata metadata, PathInits inits) {
        this(ProductVariants.class, metadata, inits);
    }

    public QProductVariants(Class<? extends ProductVariants> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

