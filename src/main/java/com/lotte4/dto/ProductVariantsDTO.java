package com.lotte4.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVariantsDTO {

    private int variant_id;
    private String sku; // 제품 고유 네이밍(ex 검은색 S 티셔츠 = GTXEM-101 / 검은색 M 티셔츠 = GTXEM-102)
    private int price;
    private int stock;
    private Map<String, String> options; // sku에 대한 옵션(ex 검은색 S / 파란색 L)
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    //외래키 목록
    private int productNo;
}
