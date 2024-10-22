package com.lotte4.dto;

import com.lotte4.entity.User;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardRegisterDTO {

    private String type;
    private int cate;
    private int title;
    private String content;
    private String regIp;
    private int state;
    private String comment;
    // 추가필드
    private String uid;


}
