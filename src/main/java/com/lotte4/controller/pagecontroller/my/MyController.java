package com.lotte4.controller.pagecontroller.my;

import com.lotte4.dto.MemberInfoDTO;
import com.lotte4.dto.ReviewDTO;
import com.lotte4.dto.UserDTO;
import com.lotte4.service.MemberInfoService;
import com.lotte4.service.UserService;
import com.lotte4.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/*

    - 2024-10-28 강중원 - 리뷰 컨트롤러 수정
    - 2024-11-05 황수빈 - 리뷰 컨트롤러 mongoDB 변환

 */

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/my")
@Controller
public class MyController {

    private final ReviewService reviewService;
    private final MemberInfoService memberInfoService;
    private final UserService userService;

    @GetMapping("/home")
    public String home(Model model) {

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
    public String review(Model model, Principal principal,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        String uid = principal.getName(); // 현재 로그인한 사용자

        Page<ReviewDTO> reviews =  reviewService.findReviewsByUid(uid, pageable);
        model.addAttribute("reviews",reviews);
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
    @PutMapping("/info/update")
    public ResponseEntity<Void> updateInfo(@RequestBody MemberInfoDTO memberInfoDTO) {
        log.info("controller>memberInfoDTO : " + memberInfoDTO);

        memberInfoService.updateMember(memberInfoDTO);
        return ResponseEntity.ok().build();
    }


}
