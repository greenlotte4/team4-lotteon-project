package com.lotte4.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product_Cate")
public class ProductCate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productCateId;

    private int parent;
    private String name;
    //외래키 목록
    private int child;
}
