package com.lotte4.oauth2;

import com.lotte4.entity.MemberInfo;
import com.lotte4.entity.Point;
import com.lotte4.entity.User;
import com.lotte4.repository.PointRepository;
import com.lotte4.repository.UserRepository;
import com.lotte4.security.MyUserDetails;
import com.lotte4.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class MyOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final PointRepository pointRepository;
    private final UserService userService;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {

            log.info("loadUser...1 : " + userRequest);

            String accessToken = userRequest.getAccessToken().getTokenValue();
            log.info("loadUser...2 : " + accessToken);

            String provider = userRequest.getClientRegistration().getRegistrationId();
            log.info("loadUser...3 : " + provider);

            OAuth2User oAuth2User = super.loadUser(userRequest);
            log.info("loadUser...4 : " + oAuth2User);

            Map<String, Object> attributes = oAuth2User.getAttributes();
            log.info("loadUser...5 : " + attributes);

            // 사용자 확인 및 회원가입 처리
            String email = (String) attributes.get("email");
            String uid = email.split("@")[0];
            String name = attributes.get("given_name").toString();


            Optional<User> optUser = userRepository.findByUid(uid);

            if(optUser.isPresent()) {
                // 회원 존재하면 시큐리티 인증처리(로그인)
                User user = optUser.get();

                // 로그인 날짜 업데이트
                userService.updateLastLoginDate(user.getUid());

                return MyUserDetails.builder()
                        .user(user)
                        .accessToken(accessToken)
                        .attributes(attributes)
                        .build();
            }else {
                // 신규 회원일 경우 User 및 MemberInfo 생성
                MemberInfo memberInfo = MemberInfo.builder()
                        .name(name)
                        .email(email)
                        .status("정상") // 기본값 설정
                        .grade("FAMILY")  // 기본값 설정
                        .point(5000)
                        .build();

                User user = User.builder()
                                .uid(uid)
                                .memberInfo(memberInfo)
                                .role("member") // 기본 역할 설정
                                .build();

                userRepository.save(user);

                // 로그인 날짜 업데이트
                userService.updateLastLoginDate(user.getUid());

                pointRepository.save( Point.builder()
                        .pointName("회원가입 축하 포인트")
                        .memberInfo(memberInfo)
                        .type("적립")
                        .point(5000)
                        .presentPoint(5000)
                        .build() );

                return MyUserDetails.builder()
                        .user(user)
                        .accessToken(accessToken)
                        .attributes(attributes)
                        .build();
            }
        } else {
            return null;
        }



    }
}