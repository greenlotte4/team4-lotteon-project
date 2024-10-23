package com.lotte4.service;

import com.lotte4.dto.MemberInfoDTO;
import com.lotte4.dto.SellerInfoDTO;
import com.lotte4.dto.UserDTO;
import com.lotte4.entity.Address;
import com.lotte4.entity.MemberInfo;
import com.lotte4.repository.MemberInfoRepository;
import com.lotte4.repository.SellerInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberInfoService {

    private final MemberInfoRepository memberInfoRepository;
    private final ModelMapper modelMapper;

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

    // member 사용자 조회
    public MemberInfoDTO selectMemberInfo(int memberInfoId) {
        return memberInfoRepository.findByMemberInfoId(memberInfoId)
                .map(memberInfo -> modelMapper.map(memberInfo, MemberInfoDTO.class))
                .orElse(null);
    }

    // 나의설정 정보 수정
    @Transactional
    public MemberInfoDTO updateMemberInfo(MemberInfoDTO memberInfoDTO) {

        log.info("memberInfoDTO: " + memberInfoDTO);

        // 기존 값을 가져옴
        MemberInfo existingMemberInfo = memberInfoRepository.findByMemberInfoId(memberInfoDTO.getMemberInfoId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
        log.info("existingMemberInfo: " + existingMemberInfo);

        if(memberInfoDTO.getEmail()!=null){
            existingMemberInfo.setEmail(memberInfoDTO.getEmail());
        }
        if(memberInfoDTO.getHp()!=null){
            existingMemberInfo.setHp(memberInfoDTO.getHp());
        }
        if(memberInfoDTO.getAddress()!=null){
            Object address = memberInfoDTO.getAddress();
            existingMemberInfo.setAddress((Address) address);
        }

        MemberInfo updatedMember = memberInfoRepository.save(existingMemberInfo);

        return updatedMember.toDTO();

    }


}


