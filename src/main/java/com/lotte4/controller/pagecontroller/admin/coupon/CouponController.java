package com.lotte4.controller.pagecontroller.admin.coupon;

import com.lotte4.dto.CouponDTO;
import com.lotte4.service.admin.config.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
public class CouponController {

    private final CouponService couponService;

    //쿠폰목록
    @GetMapping("/admin/coupon/list")
    public String AdminCouponList(Model model) {
        List<CouponDTO> couponDTOList = couponService.getAllCoupons();
        model.addAttribute("couponDTOList", couponDTOList);
        log.info(couponDTOList);
        return "/admin/coupon/list";
    }

    @PostMapping("/admin/coupon/list")
    @ResponseBody
    public String AdminCouponInsert(@RequestBody CouponDTO couponDTO, Model model) {
        log.info(couponDTO);
        try{
            couponService.insertCoupon(couponDTO);
            return "success";
        }
        catch (Exception e){
            log.error(e);
            return "fail";
        }
    }

    //쿠폰발급현황
    @GetMapping("/admin/coupon/issued")
    public String AdminCouponIssued() {
        return "/admin/coupon/issued";
    }
}
