package com.lotte4.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "member")
@Entity
public class MemberInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberInfoId;
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
