package com.lotte4.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetailDTO {
    //외래키 & 기본키
    private int productDetailId;

    private String condition;
    private String duty;
    private String receipt;
    private String sellerType;
    private String brand;
    private String COA;
    private String creator;
    private String country;
    private String warning;
    private String createDate;
    private String quality;
    private String as;
    private String asPhone;
}
