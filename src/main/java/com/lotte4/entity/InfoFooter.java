package com.lotte4.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "info_footer")
public class InfoFooter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int InfoFooterId;

    // 기업 정보
    private String footerLogo;
    private String companyName;
    private String companyOwner;
    private String MailOrderSalesApprovalNumber;
    private String companyAddress;
    private String companyAddress2;

    // 고객센터 정보
    private String csHp;
    private String csWorkingHours;
    private String csEmail;
    private String consumer;

    // 카피라이트
    private String copyright;

}