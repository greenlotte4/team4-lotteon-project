package com.lotte4.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "order_Detail")
public class order_Detail {
    @Id
    private int detail_No;

    private int count;
    private int price;
    private int discount;
    private int point;
    private int deliveryFee;
    private int total;
    //외래키 목록
    private int prod_No;
    private int order_No;
}
