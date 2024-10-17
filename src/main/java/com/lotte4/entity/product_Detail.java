package com.lotte4.entity;

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
public class product_Detail {
    //외래키 & 기본키
    @Id
    private int prod_No;

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
