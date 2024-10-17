package com.lotte4.controller.pagecontroller.admin.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminMemberController {

    @GetMapping("/admin/member/list")
    public String Adminmemberlist(){
        return "/admin/member/list";
    }

    @GetMapping("/admin/member/point")
    public String Adminmemberpoint(){
        return "/admin/member/point";
    }
}
