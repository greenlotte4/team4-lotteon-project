/*
     날짜 : 2024/10/30
     이름 : ???
     내용 : ProductDetailDTO 생성

     수정이력
     - 2024/10/30 전규찬 - productId 필드 추가
*/

package com.lotte4.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetailDTO {
    //외래키 & 기본키
    private int productDetailId;

    private String condition_field;
    private String duty;
    private String receipt;
    private String sellerType;
    private String brand;
    private String coa;
    private String creator;
    private String country;
    private String warning;
    private String createDate;
    private String quality;
    private int productId; // 241030 필드 추가

    private String as_field;
    private String asPhone;
}
