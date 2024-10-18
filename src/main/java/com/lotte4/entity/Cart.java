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
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;
    // 갯수
    private int count;
    //가격
    private int price;
    //할인
    private int discount;
    // 포인트
    private int point;
    // 배달비
    private int deliveryFee;
    // 총계
    private int total;
    // 날짜
    private LocalDateTime rDate;


    //외래키 목록
    private int user_Id;
    private int prod_No;
}
