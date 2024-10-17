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
@Table(name = "product")
public class product {
    @Id
    private int prod_No;

    private String name;
    private String company;
    private int price;
    private int discount;
    private int point;
    private int stock;
    private int sold;
    private int deliveryFee;
    private int hit;
    private int review;
    private String img1;
    private String img2;
    private String img3;
    private String detail;
    private int status;
    //외래키 목록
    private int cate_Id;
    private int seller_Id;
}
