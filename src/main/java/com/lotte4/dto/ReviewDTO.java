package com.lotte4.dto;

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
    private int reviewId;

    private int reviewStar;

    private String content;

    private String regIp;

    private String img1;

    private String img2;

    private String img3;

    private LocalDateTime regDate;

    private String regDateSub;
    //외래키

    private ProductDTO product;

    private UserDTO user;
}
