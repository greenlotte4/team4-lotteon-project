package com.lotte4.controller.pagecontroller.admin.coupon;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CouponController {
    
    //쿠폰목록
    @GetMapping("/admin/coupon/list")
    public String AdmincouponList() {
        return "/admin/coupon/list";
    }

    //쿠폰발급현황
    @GetMapping("/admin/coupon/issued")
    public String AdmincouponIssued() {
        return "/admin/coupon/issued";
    }
    



}
