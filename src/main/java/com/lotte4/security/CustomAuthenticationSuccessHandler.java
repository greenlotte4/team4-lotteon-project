
package com.lotte4.security;

import com.lotte4.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler  {

   /* private final UserService userService; // 사용자의 로그인 정보를 업데이트할 서비스

    public CustomAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        try {
            String username = authentication.getName(); // 로그인한 사용자 이름
            log.info("username : " + username);
            userService.updateLastLogin(username); // 로그인 날짜 업데이트 메서드 호출
            response.sendRedirect("/index"); // 로그인 성공 후 리디렉션할 URL
        } catch (Exception e) {
            log.error("Error during authentication success handling", e);
            response.sendRedirect("/member/login?error=true"); // 오류가 발생하면 로그인 페이지로 리디렉션
        }
    }*/

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 로그인 성공 시, remember-me 파라미터 체크
        String rememberMe = request.getParameter("remember-me");

        // 자동 로그인 체크가 활성화된 경우, 로그를 찍어보거나 추가 작업을 할 수 있습니다.
        if ("on".equals(rememberMe)) {
            log.info("자동 로그인 활성화됨");
        } else {
            log.info("자동 로그인 비활성화됨");
        }

        // 역할에 따라 리다이렉트할 URL 설정
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .orElse("ROLE_member"); // 기본값 설정

        // 역할에 따라 리다이렉트할 URL 결정
        String redirectUrl;
        if (role.equals("ROLE_ADMIN") || role.equals("ROLE_seller")) {
            redirectUrl = "/lotteon/admin/index"; // 관리자로 로그인 시 리다이렉트
        } else {
            redirectUrl = "/lotteon/index"; // 일반 사용자 로그인 시 리다이렉트
        }

        response.sendRedirect(redirectUrl);
    }


}
