package com.lotte4.dto.coupon;

import com.lotte4.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponIssuedResponseDTO {

    private int IssueId;

    private CouponDTO coupon;
    private User user;
    private int status;
    //사용일
    private LocalDateTime uDate;
}
