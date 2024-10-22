package com.lotte4.entity;

import com.lotte4.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    // 아이디
    private String uid;
    // 비밀번호
    private String pass;
    // 역할
    private String role;
    //
    @CreationTimestamp
    private String createdAt;
    private String leaveDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_info_id")
    private MemberInfo memberInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_info_id")
    private SellerInfo sellerInfo;


    public UserDTO toDTO() {
        return UserDTO.builder()
                .userId(userId)
                .memberInfo(memberInfo.toDTO())
                .sellerInfo(sellerInfo.toDTO())
                .uid(uid)
                .pass(pass)
                .role(role)
                .createdAt(createdAt)
                .leaveDate(leaveDate)
                .build();
    }

}