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
    @GetMapping("/order")
    public String order(@RequestParam(required = false) String period,
                        @RequestParam(required = false) Integer month,
                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate startDate,
                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate endDate,
                        @AuthenticationPrincipal MyUserDetails myUserDetails,
                        Model model) {

        log.info("period : " + period);
        log.info("month : " + month);
        log.info("startDate : " + startDate);
        log.info("endDate : " + endDate);

        // null 이 아닌 값을 기준으로 기간별 주문 목록을 조회하기
        MemberInfo memberInfo = myUserDetails.getUser().getMemberInfo();

        if (period != null) {

            // 현재 시점으로부터 7, 15, 30일 이내의 주문 목록 조회
            List<OrderDTO> orderDTOS = orderService.getOrdersByRelativePeriod(period, memberInfo);
            model.addAttribute("orderDTOs", orderDTOS);
            model.addAttribute("selected_period", period);

        } else if (month != null) {

            // 특정 달에 대한 주문 목록 조회
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
            int year = Integer.parseInt(LocalDate.now().format(formatter));
            List<OrderDTO> orderDTOS = orderService.getOrdersByMonth(memberInfo, month, year);
            model.addAttribute("orderDTOs", orderDTOS);
            model.addAttribute("selected_month", month+"월");

        } else if (startDate != null && endDate != null) {

            // LocalDate 로 받은 값을 LocalDateTime 으로 변환(시분초는 00시 00분 00초 로 고정)
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atStartOfDay();

            // 사용자가 지정한 기간에 대한 주문 목록 조회
            List<OrderDTO> orderDTOS = orderService.getOrdersByCustomDateRange(memberInfo, startDateTime, endDateTime);
            model.addAttribute("orderDTOs", orderDTOS);

        } else {

            // 특정한 기간 설정이 없을 경우 전체 주문 목록 최신순으로 조회
            List<OrderDTO> orderDTOS = orderService.selectAllByMemberInfoOrderByDateDesc(memberInfo);
            model.addAttribute("orderDTOs", orderDTOS);
        }

        // 최근 5개월 출력을 위한 데이터
        List<String> lastFiveMonths = getLastFiveMonths();
        model.addAttribute("months", lastFiveMonths);

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

        Page<ReviewDTO> reviews = reviewService.findReviewsByUid(uid, pageable);
        model.addAttribute("reviews", reviews);

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
