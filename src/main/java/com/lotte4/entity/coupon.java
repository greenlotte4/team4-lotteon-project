package com.lotte4.entity;

import jakarta.persistence.Entity;
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
@Table(name = "coupon")
public class coupon {
    @Id
    private int coupon_Id;
    private String couponName;
    private int discountPrice;
    private LocalDateTime useDate;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String status;

    //외래키 목록
    private int member_Id;
    private int order_Id;
}
