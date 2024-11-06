package com.lotte4.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
//    private final MyOauth2UserService myOauth2UserService;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final MyUserDetailsService myUserDetailsService;



    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.formLogin(login -> login
                .loginPage("/member/login")
                .successHandler(customAuthenticationSuccessHandler)
                .failureUrl("/member/login?success=100")
                .usernameParameter("uid")
                .passwordParameter("pass"));

        http.rememberMe(rememberMe -> rememberMe
                .key("lotteonlogincookiekey")
                .tokenValiditySeconds(60 * 60 * 24) // 24시간을 초단위로 설정
                .rememberMeParameter("remember-me")
                .userDetailsService(myUserDetailsService));

        http.logout(logout -> logout
                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .deleteCookies("JSESSIONID", "remember-me"));  // 쿠키 삭제);

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/market/**").permitAll()
                .requestMatchers("/article/**").permitAll()
                .requestMatchers("/intro/**").permitAll()
                .requestMatchers("/user/**").permitAll()
                .anyRequest().permitAll());

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




}
