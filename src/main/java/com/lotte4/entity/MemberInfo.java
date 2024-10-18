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
    @Enumerated(EnumType.ORDINAL)
    private status status;
    @Enumerated(EnumType.ORDINAL)
    private grade grade;


    public enum status{
        정상, // 0번
        중지, // 1번
        휴면, // 2번
        탈퇴  // 3번
    }

    public enum grade{
        ADMIN,  //0번
        FAMILY, //1번
        SILVER, //2번
        GOLD,   //3번
        VIP,    //4번
        VVIP,   //5번
        SELLER, //6번
    }

}
