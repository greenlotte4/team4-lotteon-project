package com.lotte4.controller.pagecontroller.admin.member;

import com.lotte4.dto.PointDTO;
import com.lotte4.dto.UserDTO;
import com.lotte4.service.PointService;
import com.lotte4.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
/*
     날짜 : 2024/10/28
     이름 : 강은경
     내용 : AdminMemberController 생성

     수정이력
      - 2024/10/28 강은경 - 관리자 회원목록 기능 검색&페이징 메서드 추가
      - 2024/10/30 황수빈 - @RequiredArgsConstructor 추가, admin/point/list 추가
*/
@Log4j2
@Controller
@RequiredArgsConstructor
public class AdminMemberController {

    private final UserService userService;
    private final PointService pointService;


    @GetMapping("/admin/member/list")
    public String AdminMemberList(Model model,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size,
                                  @RequestParam(required = false) String keyword,
                                  @RequestParam(required = false) String searchCategory) { // searchCategory 추가

        // role이 member인 회원 목록 출력
        String role = "member";

        // role에 해당하는 전체 회원 수를 가져옴
        long totalElements = userService.getTotalUserCountByRoleAndKeyword(role, keyword); // keyword에 따른 총 개수 가져오기
        // 회원 목록을 가져옴
        Page<UserDTO> userList = userService.selectUserListByMember(role, page, size, keyword); // 검색 조건 추가

        // 시작 번호 계산 (검색된 결과에 따른 시작 번호)
        int startNo = (int) totalElements - (page * size);

        model.addAttribute("userList", userList);
        model.addAttribute("totalPages", userList.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalElements", totalElements);
        model.addAttribute("startNo", startNo); // 시작 번호 추가
        model.addAttribute("keyword", keyword); // keyword를 모델에 추가
        model.addAttribute("searchCategory", searchCategory); // searchCategory를 모델에 추가

        log.info("userList: " + userList);
        log.info("totalPages: " + userList.getTotalPages());
        log.info("currentPage: " + page);
        log.info("size: " + size);
        log.info("totalElements: " + totalElements);
        log.info("startNo: " + startNo);
        log.info("keyword: " + keyword);
        log.info("userList size: " + userList.getContent().size());

        return "/admin/member/list";
    }

    @GetMapping("/admin/member/list/{uid}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String uid) {
        UserDTO userDTO = userService.selectUser(uid);
        log.info("userDTO: " + userDTO);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseBody
    @GetMapping("/member/point")
    public ResponseEntity<Page<PointDTO>> pointfinder (@RequestParam(required = false) String searchType,
                                                       @RequestParam(required = false) String keyword,
                                                       @RequestParam(required = false) String type,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "8") int size) {
        Pageable pageable = PageRequest.of(page, size);

        // 공통 메서드인 `searchPoints`를 호출하여 모든 조건을 처리
        Page<PointDTO> results = pointService.searchPoints(type, searchType, keyword, pageable);

        return ResponseEntity.ok(results);
    }

    @GetMapping("/admin/member/point")
    public String Adminmemberpoint(Model model,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "8") int size) {

        return "/admin/member/point";
    }
}
