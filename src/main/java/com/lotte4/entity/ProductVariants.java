package com.lotte4.entity;

import com.lotte4.config.MapToJsonConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@ToString(exclude = {"options"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_variants")
public class ProductVariants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int variant_id;

    private String sku; // 제품 고유 네이밍(ex 검은색 S 티셔츠 = GTXEM-101 / 검은색 M 티셔츠 = GTXEM-102)
    private int price;
    private int stock;

    @Convert(converter = MapToJsonConverter.class)
    private Map<String, String> options; // sku에 대한 옵션(ex 검은색 S / 파란색 L)

    @CurrentTimestamp
    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;
}
