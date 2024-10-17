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
@Table(name = "point")
public class point {
    @Id
    private int point_No;
    // 포인트
    private int point;
    private LocalDateTime pointDate;
    private String pointName;
    private int status;

    //외래키
    private int member_Id;
    private int order_No;
}
