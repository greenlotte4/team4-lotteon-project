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
@Table(name = "order")
public class order {

    @Id
    private int order_No;
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
