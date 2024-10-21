package com.lotte4.controller.pagecontroller;

import com.lotte4.dto.UserDTO;
import com.lotte4.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
public class MemberController {

    private final UserService userService;

    public MemberController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/member/login")
    public String login() {

        log.info("login");
            return "/member/login";
    }

    @GetMapping("/member/join")
    public String join(){
        return "/member/join";
    }

    @GetMapping("/member/signup")
    public String signup(){
        return "/member/signup";
    }

    @GetMapping("/member/signup_seller")
    public String signupseller(){
        return "/member/signup_seller";
    }

    @GetMapping("/member/register")
    public String register(){
        return "/member/register";
    }

    @PostMapping("/member/register")
    public String register(HttpServletRequest req, UserDTO userDTO){

        log.info(userDTO);

        userService.insertMemberUser(userDTO);
        log.info("insert 성공");

        return "redirect:/member/login?success=200";
    }

    @GetMapping("/member/register_seller")
    public String registerseller(){
        return "/member/register_seller";
    }

    @PostMapping("/member/register_seller")
    public String registerseller(HttpServletRequest req, UserDTO userDTO){

        log.info(userDTO);

        userService.insertSellerUser(userDTO);
        log.info("insert 성공");

        return "redirect:/member/login?success=200";
    }

    @GetMapping("/member/logout")
    public String logout(){
        return "redirect:/index";
    }

    }