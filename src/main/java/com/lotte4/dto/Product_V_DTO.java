package com.lotte4.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lotte4.entity.Product;
import com.lotte4.entity.ProductVariants;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product_V_DTO {
    private int productId;
    private String name;
    private String description;
    private String company;
    private int price;
    private int discount;
    private int point;
    private int sold;
    private int deliveryFee;
    private int hit;
    private int review;
    private String img1;
    private String img2;
    private String img3;
    private String detail;
    private SellerInfoDTO sellerInfoId;

    @Builder.Default
    private List<ProductVariants> productVariants = new ArrayList<>();

    private Map<String, List<String>> options;
    private int status;
    private ProductCateDTO productCateId;

    public Product_V_DTO(Product product) {
        this.productId = product.getProductId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.company = product.getCompany();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
        this.point = product.getPoint();
        this.sold = product.getSold();
        this.deliveryFee = product.getDeliveryFee();
        this.hit = product.getHit();
        this.review = product.getReview();
        this.img1 = product.getImg1();
        this.img2 = product.getImg2();
        this.img3 = product.getImg3();
        this.detail = product.getDetail();
        this.productVariants = product.getProductVariants();
    }


}

