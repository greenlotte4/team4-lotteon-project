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
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member", referencedColumnName = "member_id")
    private MemberInfo memberInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_detail", referencedColumnName = "seller_id")
    private SellerInfo sellerInfo;

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
    private int memberInfoId;
    private int sellerInfoId;




}
