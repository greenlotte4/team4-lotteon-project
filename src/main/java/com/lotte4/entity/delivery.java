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
@Table(name = "delivery")
public class delivery {
    @Id
    private int deliver_No;

    private LocalDateTime Date;
    private String Company;
    private String wayBill;
    private LocalDateTime wayBillDate;
    private String etc;

    //외래키
    private int order_No;

}
