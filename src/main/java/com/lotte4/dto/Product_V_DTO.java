/*
     날짜 : 2024/10/29
     이름 : 전규찬
     내용 : Product_V_DTO 생성

     수정이력
*/

package com.lotte4.dto;

import com.lotte4.entity.Product;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<ProductVariantsWithoutProductDTO> productVariants = new ArrayList<>();

    private LinkedHashMap<String, List<String>> options;
    private int status;
    private ProductCateDTO productCateId;
    private ProductDetailDTO productDetailId;

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
        this.status = product.getStatus();
        this.options = product.getOptions();

        // SellerInfoDTO 매핑
        if (product.getSellerInfoId() != null) {
            this.sellerInfoId = new SellerInfoDTO(product.getSellerInfoId());
        }

        // ProductCateDTO 매핑
        if (product.getProductCateId() != null) {
            this.productCateId = new ProductCateDTO(product.getProductCateId());
        }

        // ProductVariants 매핑
        if (product.getProductVariants() != null && !product.getProductVariants().isEmpty()) {
            this.productVariants = product.getProductVariants().stream()
                    .map(ProductVariantsWithoutProductDTO::new)
                    .collect(Collectors.toList());
        }

        // ProductDetailDTO 매핑
        if (product.getProductDetail() != null) {
            this.productDetailId = new ProductDetailDTO(product.getProductDetail());
        }
    }

}

