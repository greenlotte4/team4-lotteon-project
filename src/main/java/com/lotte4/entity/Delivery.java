package com.lotte4.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "delivery")
@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryNo;


    //배송 시작일
    @CreationTimestamp
    private LocalDateTime deliveryDate = LocalDateTime.now();

    //배송 무조건 완료일
    private LocalDateTime deliveryTime = deliveryDate = deliveryDate.plusDays(3);

    //배송기업
    private String deliveryCompany;

    //송장번호
    private String deliveryWaybill;

    //송장생성일자
    @CreationTimestamp
    private LocalDateTime waybillDate;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    private Order order;

}

