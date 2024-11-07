package com.lotte4.dto;

import com.lotte4.entity.ProductVariants;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {

    private String uid;
    private int variantId;
    private int prodId;
    private int rating;
    private String content;
    private String regIp;
    private String img1;
    private String img2;
    private LocalDateTime regDate;

    // 추가 필드
    private ProductVariants productVariants;



}
