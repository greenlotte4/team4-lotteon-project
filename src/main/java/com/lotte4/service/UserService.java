package com.lotte4.service;

import com.lotte4.dto.UserDTO;
import com.lotte4.entity.MemberInfo;
import com.lotte4.entity.SellerInfo;
import com.lotte4.entity.User;
import com.lotte4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final MemberInfoService memberInfoService;
    private final SellerInfoService sellerInfoService;
    private final ModelMapper modelMapper;

    // 일반회원 회원가입
    public void insertMemberUser(UserDTO userDTO) {

        // MemberInfo 저장
        MemberInfo memberInfo = memberInfoService.insertMemberInfo(userDTO.getMemberInfo());

        // User 저장
        User user = User.builder()
                .uid(userDTO.getUid())
                .pass(userDTO.getPass())
                .role(userDTO.getRole())
                .createdAt(userDTO.getCreatedAt())
                .leaveDate(userDTO.getLeaveDate())
                .memberInfo(memberInfo)
                .build();

        userRepository.save(user);

        log.info("memberuser" + user);

    }

    // 판매자 회원가입
    public void insertSellerUser(UserDTO userDTO) {

        // SellerInfo 저장
        SellerInfo sellerInfo = sellerInfoService.insertSellerInfo(userDTO.getSellerInfo());

        // User 저장
        User user = User.builder()
                .uid(userDTO.getUid())
                .pass(userDTO.getPass())
                .role(userDTO.getRole())
                .createdAt(userDTO.getCreatedAt())
                .leaveDate(userDTO.getLeaveDate())
                .sellerInfo(sellerInfo)
                .build();

        userRepository.save(user);

        log.info("selleruser" + user);

    }

    // 사용자 조회
    public UserDTO selectUser(String uid) {
        return userRepository.findByUid(uid)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElse(null);
    }





}
