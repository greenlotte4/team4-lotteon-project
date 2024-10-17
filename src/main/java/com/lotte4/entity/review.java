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
@Table(name = "review")
public class review {
    @Id
    private int review_No;

    private int reviewStar;
    private String content;
    private String regIp;
    private LocalDateTime regDate;

    //외래키
    private int prod_No;
    private int user_Id;

}
