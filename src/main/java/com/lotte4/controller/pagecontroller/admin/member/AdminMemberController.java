package com.lotte4.controller.pagecontroller.admin.member;

import com.lotte4.dto.UserDTO;
import com.lotte4.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
/*
     날짜 : 2024/10/28
     이름 : 강은경
     내용 : AdminMemberController 생성

     수정이력
      - 2024/10/28 강은경 - 관리자 회원목록 기능 검색&페이징 메서드 추가
*/
@Log4j2
@Controller
public class AdminMemberController {

    private final UserService userService;

    public AdminMemberController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/member/list")
    public String AdminMemberList(Model model,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size,
                                  @RequestParam(required = false) String keyword,
                                  @RequestParam(required = false) String searchCategory) { // searchCategory 추가

        // role이 member인 회원 목록 출력
        String role = "member";

        int totalElements = userService.getTotalUserCountByRole("member");
        Page<UserDTO> userList = userService.selectUserListByMember(role, page, size, keyword, searchCategory); // 검색 조건 추가
        int startNo = totalElements - (page * size); // 시작 번호 계산

        model.addAttribute("userList", userList);
        model.addAttribute("totalPages", userList.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalElements", totalElements);
        model.addAttribute("startNo", startNo);
        model.addAttribute("keyword", keyword); // keyword를 모델에 추가
        model.addAttribute("searchCategory", searchCategory); // searchCategory를 모델에 추가

        log.info("userList: " + userList);
        log.info("totalElements: " + totalElements);
        log.info("size: " + size);
        log.info("startNo: " + startNo);
        log.info("userList size: " + userList.getContent().size());

        return "/admin/member/list";
    }


    @GetMapping("/admin/member/point")
    public String Adminmemberpoint(){
        return "/admin/member/point";
    }
}
