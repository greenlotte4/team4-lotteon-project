package com.lotte4.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDTO {
    private int orderDetailId;
    private int count;
    private String option1;
    private String option2;
    private int price;
    private int discount;
    private int point;
    private int deliveryFee;
    private int total;
}
