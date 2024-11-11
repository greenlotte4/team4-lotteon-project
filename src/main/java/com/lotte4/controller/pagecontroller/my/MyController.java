package com.lotte4.controller.pagecontroller.my;

import com.lotte4.dto.*;
import com.lotte4.dto.mongodb.ReviewDTO;
import com.lotte4.entity.MemberInfo;
import com.lotte4.entity.OrderItems;
import com.lotte4.security.MyUserDetails;
import com.lotte4.service.DeliveryService;
import com.lotte4.service.MemberInfoService;
import com.lotte4.service.OrderService;
import com.lotte4.service.UserService;
import com.lotte4.service.mongodb.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/*

    - 2024-10-28 강중원 - 리뷰 컨트롤러 수정
    - 2024-11-05 황수빈 - 리뷰 컨트롤러 mongoDB 변환
    - 2024-11-08 전규찬 - 마이페이지 출력, 기간별 조회 기능

 */

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/my")
@Controller
public class MyController {

    private final ReviewService reviewService;
    private final MemberInfoService memberInfoService;
    private final UserService userService;
    private final OrderService orderService;
    private final DeliveryService deliveryService;
    private final ModelMapper modelMapper;

    private List<String> getLastFiveMonths() {
        List<String> months = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월");

        for (int i = 4; i >= 0; i--) { // 최근 5개월 (현재 포함)
            LocalDate date = currentDate.minusMonths(i);
            months.add(date.format(formatter));
        }

        return months;
    }

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        if (myUserDetails.getUser() != null && myUserDetails.getUser().getRole().equals("member")) {
            MemberInfo memberInfo = myUserDetails.getUser().getMemberInfo();
            OrderDTO orderDTO = orderService.selectRecentOrder(memberInfo);
            List<OrderItemsDTO> orderItemsDTOs = orderDTO.getOrderItems();

            // variantsId 만 있는 orderItemsDTOs 의 variantsDTO 채워주기
            List<OrderItemsDTO> orderItems = orderService.getMissingProductVariants(orderItemsDTOs);

            String content = deliveryService.getDeliveryContentByOrderItem(modelMapper.map(orderItems.get(0), OrderItems.class));

            model.addAttribute("orderDTO", orderDTO);
            model.addAttribute("orderItems", orderItems);
            model.addAttribute("content", content);
            return "/my/home";
        }
        return "/my/home";
    }

    // 전체 주문 목록 조회
    @GetMapping("/order/{period}")
    public String order(@PathVariable(required = false) String period,@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {

        if (myUserDetails.getUser() != null && myUserDetails.getUser().getRole().equals("member")) {
            MemberInfo memberInfo = myUserDetails.getUser().getMemberInfo();
            List<OrderDTO> orderDTOS = orderService.selectAllByMemberInfoOrderByDateDesc(memberInfo);

            model.addAttribute("orderDTOs", orderDTOS);

            // 최근 5개월 출력을 위한 데이터
            List<String> lastFiveMonths = getLastFiveMonths();
            model.addAttribute("months", lastFiveMonths);

            return "/my/order";
        }

        return "/my/order";
    }

    // 1. 상대적인 기간 조회
    @GetMapping("/period/{period}")
    public String getOrdersByRelativePeriod(@PathVariable String period, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        if (myUserDetails.getUser() != null && myUserDetails.getUser().getRole().equals("member")) {
            MemberInfo memberInfo = myUserDetails.getUser().getMemberInfo();
            List<OrderDTO> orderDTOS = orderService.getOrdersByRelativePeriod(period, memberInfo);
            log.info("orderDTOS = " + orderDTOS);
            model.addAttribute("orderDTOs", orderDTOS);
            // 최근 5개월 출력을 위한 데이터
            List<String> lastFiveMonths = getLastFiveMonths();
            model.addAttribute("months", lastFiveMonths);
            return "/my/order";
        }
        return "redirect:/my/order";
    }

    // 2. 특정 월 단위 조회
    @GetMapping("/order/month")
    public String getOrdersByMonth(@RequestParam int month, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        int year = Integer.parseInt(LocalDate.now().format(formatter));
        List<OrderDTO> orderDTOS = orderService.getOrdersByMonth(month, year);
        model.addAttribute("orderDTOs", orderDTOS);
        return "/my/order";
    }

    // 3. 사용자 지정 기간 조회
    @GetMapping("/order/custom")
    public String getOrdersByCustomDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate, Model model) {
        List<OrderDTO> orderDTOS = orderService.getOrdersByCustomDateRange(startDate, endDate);
        model.addAttribute("orderDTOs", orderDTOS);
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

    // 나의 정보 탈퇴 요청 처리
    @PostMapping("/info/quit")
    public ResponseEntity<String> quitUser(@RequestBody UserDTO userDTO) {
        log.info("userDTO : " + userDTO);
        try {
            boolean success = userService.quitUser(userDTO.getUid());
            if (success) {
                return ResponseEntity.ok("탈퇴 완료");
            } else {
                return ResponseEntity.status(400).body("탈퇴 처리 실패");
            }
        } catch(Exception e) {
            return ResponseEntity.status(500).body("서버 오류");
        }
    }

}
