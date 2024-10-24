package com.lotte4.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
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
    private String sellerInfoId;
    private Map<String, List<String>> options;
    private int status;
    private int productCate_productCateId;
}

