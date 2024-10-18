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
@Table(name = "coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int couponId;
    private String couponName;
    private int discountPrice;
    private LocalDateTime useDate;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String status;

    //외래키 목록
    private int memberInfoId;
    private int orderId;
}
