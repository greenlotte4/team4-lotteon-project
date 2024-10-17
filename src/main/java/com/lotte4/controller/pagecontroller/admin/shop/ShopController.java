package com.lotte4.controller.pagecontroller.admin.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ShopController {
    
    //상점목록
    @GetMapping("/admin/shop/list")
    public String Adminshoplist(){
        return "/admin/shop/list";
    }

    // 매출현황
    @GetMapping("/admin/shop/sales")
    public String Adminshopsales(){
        return "/admin/shop/sales";
    }
}
