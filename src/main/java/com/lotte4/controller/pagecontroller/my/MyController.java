package com.lotte4.controller.pagecontroller.my;

import com.lotte4.entity.MemberInfo;
import com.lotte4.service.MemberInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/my")
@Controller
public class MyController {

    private final MemberInfoService memberInfoService;

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

   /* @GetMapping("/info/{memberInfoId}")
    public String info(@PathVariable int memberInfoId, Model model) {
        log.info("memberInfoId: " + memberInfoId);

        Optional<MemberInfo> memberInfoOptional = memberInfoService.selectMemberInfoById(memberInfoId);
        if (memberInfoOptional.isPresent()) {
            model.addAttribute("memberInfo", memberInfoOptional.get());
            return "/my/info";
        } else {
            return "redirect:/error";
        }
    }*/

   /* @GetMapping("/info")
    public String info(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // 현재 로그인한 사용자의 ID 또는 기타 정보를 가져옴
        String userId = userDetails.getUsername(); // 또는 필요한 다른 정보를 가져올 수 있습니다

        // 회원 정보를 조회
        MemberInfo memberInfo = memberInfoService.findByUserId(userId);
        model.addAttribute("memberInfo", memberInfo);

        // 판매자 정보를 조회 (optional, 판매자일 경우만 조회)
        SellerInfo sellerInfo = sellerInfoService.findByUserId(userId);
        if (sellerInfo != null) {
            model.addAttribute("sellerInfo", sellerInfo);
        }

        return "/my/info"; // 회원과 판매자 정보가 포함된 뷰로 반환
    }*/


}
