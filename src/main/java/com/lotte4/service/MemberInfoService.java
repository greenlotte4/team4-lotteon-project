package com.lotte4.service;

import com.lotte4.dto.MemberInfoDTO;
import com.lotte4.dto.SellerInfoDTO;
import com.lotte4.entity.MemberInfo;
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
public class MemberInfoService {

    private final MemberInfoRepository memberInfoRepository;

    // 일반회원 정보 저장
    public MemberInfo insertMemberInfo(MemberInfoDTO memberInfoDTO) {
        if (memberInfoDTO == null) {
            throw new IllegalArgumentException("MemberInfoDTO cannot be null");
        }

        log.info("memberInfoDTO: " + memberInfoDTO);
        MemberInfo memberInfo = memberInfoDTO.toEntity();
        return memberInfoRepository.save(memberInfo);
    }

    // 일반회원 정보 조회
    public Optional<MemberInfo> selectMemberInfoById(int memberInfoId) {
        log.info("memberInfoId: " + memberInfoId);
        return memberInfoRepository.findById(memberInfoId);
    }

}
