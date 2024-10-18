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
@Table(name = "point")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pointId;
    // 포인트
    private int point;
    private LocalDateTime pointDate;
    private String pointName;
    private String type;

    //외래키
    private int memberInfoId;
    private int orderId;
}
