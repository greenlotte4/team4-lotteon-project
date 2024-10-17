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
@Table(name = "seller")
public class seller {
    @Id
    private int seller_Id;
    // 회사 이름
    private String comName;
    // 대표자 명
    private String ceo;
    // 사업자 등록번호
    private String comNumber;
    // 통신판매업 번호
    private String bizNumber;
    // 전화 번호
    private String hp;
    // 팩스 번호
    private String fax;
    // 우편번호
    private String zipCode;
    // 주소
    private String addr1;
    // 상세주소
    private String addr2;
    // 아이피
    private String regIp;
    // 변경 일자
    private LocalDateTime updateAt;
    // 상태
    private int state;
}
