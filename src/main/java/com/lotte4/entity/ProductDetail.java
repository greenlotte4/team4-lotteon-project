package com.lotte4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_detail")
public class ProductDetail {
    //외래키 & 기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productDetailId;

    private String condition_field;
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
    private String as_field;
    private String asPhone;
}
