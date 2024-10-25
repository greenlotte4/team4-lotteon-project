package com.lotte4.dto;

import com.lotte4.entity.Order;
import com.lotte4.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponDTO {
    private int couponId;
    private String type;
    private String name;

    //benefit
    private String benefit;

    private Date sDate;
    private Date eDate;
    private int dDate;

    private int status;

    private int totalIssued;

    private int totalUsed;

    private String IDate;

    private String ect;

    //외래키
    private UserDTO users;


}
