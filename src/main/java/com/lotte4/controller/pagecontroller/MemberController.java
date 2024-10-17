package com.lotte4.controller.pagecontroller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class MemberController {

    @GetMapping("/member/login")
    public String login() {

        log.info("login");
            return "member/login";
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

    @GetMapping("/member/register_seller")
    public String registerseller(){
        return "/member/register_seller";
    }

    @GetMapping("/member/logout")
    public String logout(){
        return "redirect:/index";
    }

    }