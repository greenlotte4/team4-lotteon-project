package com.lotte4.dto;

import com.lotte4.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponseDTO {
    private int boardId;

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
    private UserDTO user;
}
