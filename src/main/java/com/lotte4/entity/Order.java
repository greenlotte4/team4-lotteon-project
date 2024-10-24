package com.lotte4.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "orders")
@Entity
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

    //결재방법
    private int Pay;
    private int Status;
    private LocalDateTime Date;
    private int couponUse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberInfoId")
    private MemberInfo memberInfo;

    @OneToOne(mappedBy = "order")
    private Coupon coupon;

    @OneToOne(mappedBy = "order")
    private Delivery delivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

}
