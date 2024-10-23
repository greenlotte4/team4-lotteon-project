package com.lotte4.entity;

import com.lotte4.config.MapToJsonConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private String name;
    private String description;
    private String company;
    private int price;
    private int discount;
    private int point;
    private int stock;
    private int sold;
    private int deliveryFee;
    private int hit;
    private int review;
    private String img1;
    private String img2;
    private String img3;
    private String detail;

    @Convert(converter = MapToJsonConverter.class)
    private Map<String, List<String>> options;

    private int status;
}

