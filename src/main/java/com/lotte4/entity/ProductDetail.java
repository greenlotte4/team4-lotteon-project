package com.lotte4.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product_option")
public class ProductDetail {
    //외래키 & 기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private LocalDateTime createDate;
    private String quality;
    private String as;
    private String asPhone;
}
