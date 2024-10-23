package com.lotte4.controller.pagecontroller;

import com.lotte4.dto.TermsDTO;
import com.lotte4.dto.UserDTO;
import com.lotte4.entity.Terms;
import com.lotte4.service.TermsService;
import com.lotte4.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final UserService userService;
    private final TermsService termsService;


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

        return "redirect:/index?success=200";
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

        return "redirect:/index?success=200";
    }

    @GetMapping("/member/logout")
    public String logout(){
        return "redirect:/index";
    }

    // 중복확인 및 이메일 인증 코드 발송
    @ResponseBody
    @GetMapping("/member/{type}/{value}")
    public ResponseEntity<?> checkMember(HttpSession session,
                                         @PathVariable("type") String type,
                                         @PathVariable("value") String value) {

        log.info("type : " + type + ", value : " + value);

        int count = userService.selectCountUser(type, value);
        log.info("count : " + count);

        // 중복 없으면 이메일 인증코드 발송
        if(count == 0 && type.equals("email")){
            log.info("email : " + value);
            userService.sendEmailCode(session, value);
        }

        // JSON 생성
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", count);


        return ResponseEntity.ok().body(resultMap);
    }

    // 이메일 인증 코드 검사
    @ResponseBody
    @PostMapping("/member/email")
    public ResponseEntity<?> checkEmail(HttpSession session, @RequestBody Map<String, String> jsonData) {
        log.info("checkEmail code : " + jsonData);

        String receiveCode = userService.sendEmailCode(session, jsonData.get("email"));
        log.info("checkEmail receiveCode : " + receiveCode);

        // 세션에 저장된 인증 코드 가져오기
        String sessionCode = (String) session.getAttribute("code");

        Map<String, Object> resultMap = new HashMap<>();
        if (sessionCode != null && sessionCode.equals(receiveCode)) {
            resultMap.put("result", true);
        } else {
            resultMap.put("result", false);
        }
        resultMap.put("code", receiveCode);

        return ResponseEntity.ok().body(resultMap);
    }

}

