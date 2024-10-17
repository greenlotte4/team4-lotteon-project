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
@Table(name = "board")
public class board {
    @Id
    private int board_id;
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
