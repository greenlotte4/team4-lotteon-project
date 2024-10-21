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

    // 유형
    private String cate;
    private String type;
    // 제목
    private String title;
    // 내용
    private String content;
    // 작성한 시간
    private LocalDateTime regDate;
    // 작성자 Ip
    private String regIp;
    // 상태
    private int state;
    // 답변
    private String comment;

    private String uid;
    // TODO : 나중에는 User로 변경
}
