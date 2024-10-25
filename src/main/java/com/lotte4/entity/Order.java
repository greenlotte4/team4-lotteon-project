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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberInfoId") // 이 컬럼은 MemberInfo 엔티티의 id와 매핑되어야 함
    private MemberInfo memberInfo;

    @OneToOne
    @JoinColumn(name = "couponId")
    private Coupon coupon;

    @OneToOne(mappedBy = "order")
    private Delivery delivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

}
