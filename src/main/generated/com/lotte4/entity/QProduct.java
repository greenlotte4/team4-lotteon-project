package com.lotte4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -611627117L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final StringPath company = createString("company");

    public final NumberPath<Integer> deliveryFee = createNumber("deliveryFee", Integer.class);

    public final StringPath description = createString("description");

    public final StringPath detail = createString("detail");

    public final NumberPath<Integer> discount = createNumber("discount", Integer.class);

    public final NumberPath<Integer> hit = createNumber("hit", Integer.class);

    public final StringPath img1 = createString("img1");

    public final StringPath img2 = createString("img2");

    public final StringPath img3 = createString("img3");

    public final StringPath name = createString("name");

    public final MapPath<String, java.util.List<String>, SimplePath<java.util.List<String>>> options = this.<String, java.util.List<String>, SimplePath<java.util.List<String>>>createMap("options", String.class, java.util.List.class, SimplePath.class);

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final QProductCate productCateId;

    public final QProductDetail productDetail;

    public final NumberPath<Integer> productId = createNumber("productId", Integer.class);

    public final ListPath<ProductVariants, QProductVariants> productVariants = this.<ProductVariants, QProductVariants>createList("productVariants", ProductVariants.class, QProductVariants.class, PathInits.DIRECT2);

    public final NumberPath<Integer> review = createNumber("review", Integer.class);

    public final QSellerInfo sellerInfoId;

    public final NumberPath<Integer> sold = createNumber("sold", Integer.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.productCateId = inits.isInitialized("productCateId") ? new QProductCate(forProperty("productCateId"), inits.get("productCateId")) : null;
        this.productDetail = inits.isInitialized("productDetail") ? new QProductDetail(forProperty("productDetail")) : null;
        this.sellerInfoId = inits.isInitialized("sellerInfoId") ? new QSellerInfo(forProperty("sellerInfoId"), inits.get("sellerInfoId")) : null;
    }

}

