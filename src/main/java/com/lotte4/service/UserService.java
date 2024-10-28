package com.lotte4.service;

import com.lotte4.dto.CartDTO;
import com.lotte4.dto.UserDTO;
import com.lotte4.entity.MemberInfo;
import com.lotte4.entity.SellerInfo;
import com.lotte4.entity.User;
import com.lotte4.repository.MemberInfoRepository;
import com.lotte4.repository.UserRepository;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
/*
     날짜 : 2024/10/28
     이름 : 강은경
     내용 : UserService 생성

     수정이력
      - 2024/10/28 강은경 - 관리자 회원목록 기능 검색&페이징 메서드 추가
*/
@Log4j2
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final MemberInfoService memberInfoService;
    private final SellerInfoService sellerInfoService;
    private final ModelMapper modelMapper;
    private final JavaMailSender javaMailSender;
    private final MemberInfoRepository memberInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String sender;

    // 일반회원 회원가입
    public void insertMemberUser(UserDTO userDTO) {

        // 비밀번호 인코딩
        String encoded = passwordEncoder.encode(userDTO.getPass());
        userDTO.setPass(encoded);

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

        // 비밀번호 인코딩
        String encoded = passwordEncoder.encode(userDTO.getPass());
        userDTO.setPass(encoded);

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

    public UserDTO login(String uid, String pass) {
        // UserRepository를 사용하여 userId로 사용자 정보 조회
        Optional<User> userOptional = userRepository.findByUid(uid);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // 비밀번호 검증 (PasswordEncoder를 사용하여 매칭 확인)
            if (passwordEncoder.matches(pass, user.getPass())) {
                log.info("로그인 성공 - uid: " + uid);
                // User 엔티티를 UserDTO로 매핑하여 반환
                return modelMapper.map(user, UserDTO.class);
            } else {
                log.warn("비밀번호가 일치하지 않습니다 - uid: " + uid);
            }
        } else {
            log.warn("존재하지 않는 사용자 - uid: " + uid);
        }

        return null; // 로그인 실패 시 null 반환
    }


    // 사용자 조회
    public UserDTO selectUser(String uid) {
        return userRepository.findByUid(uid)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElse(null);
    }

    // 사용자 중복 체크
    public int selectCountUser(String type, String value){
        int count = 0;

        if(type.equals("uid")){
            count = userRepository.countByUid(value);
        } else if(type.equals("email")){
            count = memberInfoRepository.countByEmail(value);
        } else if (type.equals("hp")) {
            count = memberInfoRepository.countByHp(value);
        }
        return count;
    }

    // 이메일 인증 코드 발송
    public String sendEmailCode(HttpSession session, String receiver) {

        log.info("sender : " + sender);

        // MimeMessage 생성
        MimeMessage message = javaMailSender.createMimeMessage();

        // 인증코드 생성 후 세션 저장
        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
        session.setAttribute("code", String.valueOf(code));
        log.info("code : " + code);

        String title = "lotteOn 인증코드 입니다.";
        String content = "<h1>인증코드는 " + code + "입니다.</h1>";

        try {
            message.setFrom(new InternetAddress(sender, "lotteOn", "UTF-8"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(title);
            message.setContent(content, "text/html;charset=UTF-8");

            // 메일 발송
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("sendEmailCode : " + e.getMessage());
        }

        return code + "";
    }

    // role이 member인 회원 목록 select
    public Page<UserDTO> selectUserListByMember(String role, int page, int size, String keyword, String searchCategory) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage;

        // 검색어가 있는 경우
        if (keyword != null && !keyword.isEmpty() && searchCategory != null) {
            switch (searchCategory) {
                case "uid":
                    userPage = userRepository.findByRoleAndUidContaining(role, keyword, pageable); // 아이디 검색
                    break;
                case "name":
                    userPage = userRepository.findByRoleAndMemberInfoNameContaining(role, keyword, pageable); // 이름 검색
                    break;
                case "email":
                    userPage = userRepository.findByRoleAndMemberInfoEmailContaining(role, keyword, pageable); // 이메일 검색
                    break;
                case "hp":
                    userPage = userRepository.findByRoleAndMemberInfoHpContaining(role, keyword, pageable); // 이메일 검색
                    break;
                default:
                    userPage = userRepository.findByRole(role, pageable); // 기본적으로 모든 사용자 반환
                    break;
            }
        } else {
            // 검색어가 없는 경우
            userPage = userRepository.findByRole(role, pageable);
        }

        // User 엔티티를 UserDTO로 변환
        List<UserDTO> userDTOs = userPage.getContent().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(userDTOs, pageable, userPage.getTotalElements());
    }

    // 전체 사용자 수 반환
    public int getTotalUserCountByRole(String role) {
        return userRepository.countByRole(role);
    }
}
