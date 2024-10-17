package com.lotte4.controller.pagecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/index")
    public String Adminhome(){
        return "/admin/index";
    }

}
