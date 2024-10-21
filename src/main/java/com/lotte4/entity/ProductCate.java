package com.lotte4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_Cate")
public class ProductCate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productCateId;

    // 계층
    private int depth;


    // 이름
    private String name;


    //외래키 목록
    // 부모 객체
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "parentId")
    private ProductCate parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<ProductCate> children = new ArrayList<>();


}
