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
@Table(name = "user")
public class user {
    @Id
    private int user_Id;
    // 아이디
    private String uid;
    // 비밀번호
    private String pass;
    // 역할
    private String role;
    //
    private LocalDateTime createdAt;
    private LocalDateTime leaveDate;


    //외래키 목록
    private int member_Id;
    private int seller_Id;
}
