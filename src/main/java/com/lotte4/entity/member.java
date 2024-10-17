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
@Table(name = "member")
public class member {
    @Id
    private int member_Id;
    private String name;
    private int gender;
    private String email;
    private String hp;
    private String zipCode;
    private String addr1;
    private String addr2;
    private int point;
    private String updatedAt;
    private int state;
}
