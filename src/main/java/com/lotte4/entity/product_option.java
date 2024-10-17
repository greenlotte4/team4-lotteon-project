package com.lotte4.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product_option")
public class product_option {
    @Id
    private int option_Id;

    private String name;
    private String value;
    //외래키 목록
    private int prod_No;
}
