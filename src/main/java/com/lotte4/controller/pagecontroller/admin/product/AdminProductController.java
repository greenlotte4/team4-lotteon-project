package com.lotte4.controller.pagecontroller.admin.product;

import com.lotte4.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AdminProductController {

    private final CategoryService categoryService;

    // 상품현황
    @GetMapping("/admin/product/list")
    public String AdminProductList() {
        return "/admin/product/list";
    }

    // 상품등록
    @GetMapping("/admin/product/register")
    public String AdminProductRegister(Model model) {
        model.addAttribute("productCate1List", categoryService.getProductCateListWithDepth(1));
        return "/admin/product/register";
    }






}
