package com.lotte4.dto;

import com.lotte4.entity.User;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardRegisterDTO {

    private int cate;
    private int type;
    private int title;
    private String content;
    private String regIp;
    private int state;
    private String comment;
    // 추가필드
    private String uid;
}
