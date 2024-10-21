package com.lotte4.controller.pagecontroller;

import com.lotte4.dto.TermsDTO;
import com.lotte4.dto.UserDTO;
import com.lotte4.entity.Terms;
import com.lotte4.service.TermsService;
import com.lotte4.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
public class MemberController {

    private final UserService userService;
    private final TermsService termsService;

    public MemberController(UserService userService, TermsService termsService) {
        this.userService = userService;
        this.termsService = termsService;
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

    // 약관 동의
    @GetMapping("/member/signup")
    public String signup(@RequestParam("type") int type, Model model) {

        TermsDTO termsDTO = termsService.selectTerms();
        model.addAttribute("termsDTO", termsDTO);
        model.addAttribute("type", type);
        log.info("termsDTO : " + termsDTO);

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