package com.lotte4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductDetail is a Querydsl query type for ProductDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductDetail extends EntityPathBase<ProductDetail> {

    private static final long serialVersionUID = -1382606396L;

    public static final QProductDetail productDetail = new QProductDetail("productDetail");

    public final StringPath as_field = createString("as_field");

    public final StringPath asPhone = createString("asPhone");

    public final StringPath brand = createString("brand");

    public final StringPath coa = createString("coa");

    public final StringPath condition_field = createString("condition_field");

    public final StringPath country = createString("country");

    public final StringPath createDate = createString("createDate");

    public final StringPath creator = createString("creator");

    public final StringPath duty = createString("duty");

    public final NumberPath<Integer> productDetailId = createNumber("productDetailId", Integer.class);

    public final StringPath quality = createString("quality");

    public final StringPath receipt = createString("receipt");

    public final StringPath sellerType = createString("sellerType");

    public final StringPath warning = createString("warning");

    public QProductDetail(String variable) {
        super(ProductDetail.class, forVariable(variable));
    }

    public QProductDetail(Path<? extends ProductDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductDetail(PathMetadata metadata) {
        super(ProductDetail.class, metadata);
    }

}

