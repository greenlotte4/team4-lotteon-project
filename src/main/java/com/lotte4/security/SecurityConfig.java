package com.lotte4.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
//    private final MyOauth2UserService myOauth2UserService;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        // 로그인 설정
        http.formLogin(login -> login
                                .loginPage("/member/login")
                                .defaultSuccessUrl("/index")
                                .failureUrl("/member/login?success=100")
                                .usernameParameter("uid")
                                .passwordParameter("pass"));

        // 로그아웃 설정
        http.logout(logout -> logout
                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessHandler(customLogoutSuccessHandler));


        // 인가 설정
        http.authorizeHttpRequests(authorize ->authorize
//                                                .requestMatchers("/cart/**").authenticated()
                                                .requestMatchers("/market/**").permitAll()
                                                .requestMatchers("/article/**").permitAll()
                                                .requestMatchers("/intro/**").permitAll()
                                                .requestMatchers("/user/**").permitAll()
                                                .anyRequest().permitAll());

        // 기타 보안 설정
        /*http.oauth2Login(login->login
                .loginPage("/member/login")
                .defaultSuccessUrl("/index")
                .userInfoEndpoint(endpoint->endpoint
                        .userService(myOauth2UserService)
                )
        );*/

        http.csrf(configure->configure.disable());


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




}
