package com.lotte4.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lotte4.entity.ProductVariants;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"productCate", "parent"})
public class OrderDTO {


    private int orderId;
    private int usePoint;
    private int totalPrice;

    // 배송지
    private String recipName;
    private String recipHp;
    private String recipZip;
    private String recipAddr1;
    private String recipAddr2;

    //결재방법
    private int Payment;
    private int Status;
    private LocalDateTime buyDate;
    private int couponUse; // 추후 쿠폰 ID값 받아 올 수 있도록 세팅해야함 필수 2024-11-04 조수빈
    private ProductVariantsDTO productVariants;
    private MemberInfoDTO memberInfo;
    private List<OrderItemsDTO> orderItems;


    // 배송메세지위한 추가(데이터 연결점이슈)
    private String content;
}
