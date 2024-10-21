package com.lotte4.service;

import com.lotte4.dto.MemberInfoDTO;
import com.lotte4.dto.SellerInfoDTO;
import com.lotte4.entity.MemberInfo;
import com.lotte4.entity.SellerInfo;
import com.lotte4.repository.MemberInfoRepository;
import com.lotte4.repository.SellerInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class SellerInfoService {

    private final SellerInfoRepository sellerInfoRepository;

    // 판매자 정보 저장
    public SellerInfo insertSellerInfo(SellerInfoDTO sellerInfoDTO) {

        log.info("sellerInfoDTO: " + sellerInfoDTO);
        SellerInfo sellerInfo = sellerInfoDTO.toEntity();
        return sellerInfoRepository.save(sellerInfo);

    }

    // 판매회원 정보 조회
    public Optional<SellerInfo> selectSellerInfoById(int sellerInfoId) {
        log.info("sellerInfoId: " + sellerInfoId);
        return sellerInfoRepository.findById(sellerInfoId);
    }



}
