package com.lotte4.dto;

import com.lotte4.entity.MemberInfo;
import com.lotte4.entity.SellerInfo;
import com.lotte4.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private int userId;
    private MemberInfoDTO memberInfo;
    private SellerInfoDTO sellerInfo;

    private String uid;
    private String pass;
    private String role;

    @CreationTimestamp
    private String createdAt;
    private String leaveDate;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .memberInfo(memberInfo.toEntity())
                .sellerInfo(sellerInfo.toEntity())
                .uid(uid)
                .pass(pass)
                .role(role)
                .createdAt(createdAt)
                .leaveDate(leaveDate)
                .build();
    }

}
