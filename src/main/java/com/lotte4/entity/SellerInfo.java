package com.lotte4.entity;

import com.lotte4.dto.SellerInfoDTO;
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
@Entity
@Table(name = "seller_info")
    public class SellerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sellerInfoId;
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
    
    @Embedded
    private Address address; // address 객체로 분리
    // 아이피
    private String regIp;
    // 변경 일자
    @CreationTimestamp
    private String updateAt;
    // 상태
    private int state;

    public SellerInfoDTO toDTO(){
        return SellerInfoDTO.builder()
                .sellerInfoId(sellerInfoId)
                .comName(comName)
                .ceo(ceo)
                .comNumber(comNumber)
                .bizNumber(bizNumber)
                .hp(hp)
                .fax(fax)
                .regIp(regIp)
                .updateAt(updateAt)
                .state(state)
                .build();
    }



}
