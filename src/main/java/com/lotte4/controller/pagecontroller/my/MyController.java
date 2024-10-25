package com.lotte4.controller.pagecontroller.my;

import com.lotte4.dto.MemberInfoDTO;
import com.lotte4.dto.UserDTO;
import com.lotte4.service.MemberInfoService;
import com.lotte4.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/my")
@Controller
public class MyController {

    private final MemberInfoService memberInfoService;
    private final UserService userService;

    @GetMapping("/home")
    public String home() {
        return "/my/home";
    }

    @GetMapping("/order")
    public String order() {
        return "/my/order";
    }

    @GetMapping("/point")
    public String point() {
        return "/my/point";
    }

    @GetMapping("/coupon")
    public String coupon() {
        return "/my/coupon";
    }

    @GetMapping("/review")
    public String review() {
        return "/my/review";
    }

    @GetMapping("/qna")
    public String qna() {
        return "/my/qna";
    }


//    @ResponseBody
//    @GetMapping("/info")
//    public ResponseEntity<UserDTO> infoselect(@RequestParam("uid") String uid) {
//        UserDTO userDTO = userService.selectUser(uid);
//        if(userDTO != null) {
//            return ResponseEntity.ok(userDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/info")
    public String info(Model model, Principal principal) {

        if (principal == null || principal.getName() == null) {
            return "redirect:/member/login"; // 로그인 체크
        }

        String uid = principal.getName(); // principal에서 uid 가져오기

        UserDTO userDTO = userService.selectUser(uid);

        model.addAttribute("userDTO", userDTO);
        log.info("userDTO : " + userDTO);

        return "/my/info";
    }

    // 나의설정 정보수정
    @PostMapping("/info")
    public ResponseEntity<MemberInfoDTO> updateInfo(@RequestBody MemberInfoDTO memberInfoDTO) {
        log.info("controller>memberInfoDTO : " + memberInfoDTO);

        MemberInfoDTO updatedMemberInfo = memberInfoService.updateMemberInfo(memberInfoDTO);
        log.info("updatedMemberInfo : " + updatedMemberInfo);
        return ResponseEntity.ok(updatedMemberInfo);
    }


}
