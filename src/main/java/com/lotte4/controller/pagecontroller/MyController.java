package com.lotte4.controller.pagecontroller;

import com.lotte4.dto.UserDTO;
import com.lotte4.service.MemberInfoService;
import com.lotte4.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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


    @ResponseBody
    @GetMapping("/info")
    public ResponseEntity<UserDTO> info(@RequestParam("uid") String uid) {
        UserDTO userDTO = userService.selectUser(uid);
        if(userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}
