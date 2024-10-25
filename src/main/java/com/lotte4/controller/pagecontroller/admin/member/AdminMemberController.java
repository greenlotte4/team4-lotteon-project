package com.lotte4.controller.pagecontroller.admin.member;

import com.lotte4.dto.UserDTO;
import com.lotte4.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Log4j2
@Controller
public class AdminMemberController {

    private final UserService userService;

    public AdminMemberController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/member/list")
    public String Adminmemberlist(Model model){

        // role이 member인 회원 목록 출력
        String role = "member";
        List<UserDTO> userList = userService.selectUserListByMember(role);
        model.addAttribute("userList", userList);

        log.info("userList: " + userList);

        return "/admin/member/list";
    }

    @GetMapping("/admin/member/point")
    public String Adminmemberpoint(){
        return "/admin/member/point";
    }
}
