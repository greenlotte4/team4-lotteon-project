package com.lotte4.controller.pagecontroller.admin.config;

import com.lotte4.entity.Info;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ConfigController {
    
    //배너관리
    @GetMapping("/admin/config/banner")
    public String AdminconfigBanner() {
        return "/admin/config/banner";
    }

    //약관관리
    @GetMapping("/admin/config/policy")
    public String AdminconfigPolicy() {
        return "/admin/config/policy";
    }
    
    //버전관리
    @GetMapping("/admin/config/version")
    public String AdminconfigVersion() {
        return "/admin/config/version";
    }

    // 카테고리
    @GetMapping("/admin/config/category")
    public String AdminConfigCategory() {
        return "/admin/config/category";
    }

}
