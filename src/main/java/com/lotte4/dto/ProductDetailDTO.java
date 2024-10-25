package com.lotte4.dto;

import jakarta.persistence.Column;
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

    private String condition_field;
    private String duty;
    private String receipt;
    private String sellerType;
    private String brand;
    private String coa;
    private String creator;
    private String country;
    private String warning;
    private String createDate;
    private String quality;

    private String as_field;
    private String asPhone;
}
