package com.lotte4.dto;

import com.lotte4.entity.Address;
import com.lotte4.entity.MemberInfo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Member;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInfoDTO {

    private int memberInfoId;
    private String name;
    private int gender;
    private String email;
    private String hp;
    private AddressDTO address;
    private int point;
    private String updatedAt;
    private String status;
    private String grade;

    public MemberInfo toEntity() {
        return MemberInfo.builder()
                .memberInfoId(this.memberInfoId)
                .name(this.name)
                .gender(this.gender)
                .email(this.email)
                .hp(this.hp)
                .address(new Address(address.getZipCode(), address.getAddr1(), address.getAddr2()))
                .point(this.point)
                .updatedAt(this.updatedAt)
                .status(this.status)
                .grade(this.grade)
                .build();
    }

}
