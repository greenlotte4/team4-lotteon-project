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
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private int count;
    private int price;
    private int discount;
    private int deliveryFee;
    private int usePoint;
    private int savePoint;
    private int totalPrice;
    // 영수증
    private String recipName;
    private String recipHp;
    private String recupZip;
    private String recipAddr1;
    private String recipAddr2;

    private int Pay;
    private int Status;
    private LocalDateTime Date;
    private String couponUse;

    //외래키
    private int member_id;

}