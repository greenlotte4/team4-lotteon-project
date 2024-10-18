package com.lotte4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardId;
    // 제목
    private String title;
    // 내용
    private String content;
    //
    private LocalDateTime regDate;
    //
    private String regIp;
    // 상태
    private int state;
    // 댓글
    private String comment;
}
