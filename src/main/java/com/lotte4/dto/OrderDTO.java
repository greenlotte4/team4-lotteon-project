package com.lotte4.dto;

import lombok.*;

import java.time.LocalDateTime;



@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private int orderId;
    private int count;
    private int price;
    private int discount;
    private int deliveryFee;
    private int usePoint;
    private int savePoint;
    private int totalPrice;

    //색상
    private String option1;
    //사이즈
    private String option2;

    // 배송지
    private String recipName;
    private String recipHp;
    private String recipZip;
    private String recipAddr1;
    private String recipAddr2;
    private int Pay;
    private int Status;
    private LocalDateTime Date = LocalDateTime.now();;
    private int couponUse;
    private int productId;
    private int memberInfoId;

}
