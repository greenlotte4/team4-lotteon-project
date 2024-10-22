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
@Entity
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardId;

    // 유형
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="boardCateId")
    private BoardCate boardCate;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="writer")
    private User user;
    // TODO : 나중에는 User로 변경
}
