package com.lotte4.controller.my;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/my")
@Controller
public class MyController {

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

    @GetMapping("/info")
    public String info() {
        return "/my/info";
    }

}
