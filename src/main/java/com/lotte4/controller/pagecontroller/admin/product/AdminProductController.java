package com.lotte4.controller.pagecontroller.admin.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AdminProductController {
    
    // 상품현황
    @GetMapping("/admin/product/list")
    public String AdminProductList() {
        return "/admin/product/list";
    }

    // 상품등록
    @GetMapping("/admin/product/register")
    public String AdminProductRegister() {
        return "/admin/product/register";
    }

    // 상품 카테고리
    @GetMapping("/admin/product/category")
    public String AdminProductCategory() {
        return "/admin/product/category";
    }




}
