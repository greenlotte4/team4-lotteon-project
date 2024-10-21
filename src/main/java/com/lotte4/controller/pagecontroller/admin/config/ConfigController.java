package com.lotte4.controller.pagecontroller.admin.config;

import com.lotte4.dto.ProductCateDTO;
import com.lotte4.entity.ProductCate;
import com.lotte4.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Log4j2
@AllArgsConstructor
@Controller
public class ConfigController {

    private final CategoryService categoryService;
    
    //배너관리
    @GetMapping("/admin/config/banner")
    public String AdminconfigBanner() {
        return "/admin/config/banner";
    }

    //기본설정
    @GetMapping("/admin/config/info")
    public String AdminconfigBasic() {
        return "/admin/config/info";
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
    public String AdminConfigCategory(Model model) {
        List<ProductCateDTO> productCateDTOList = categoryService.getProductCateListWithDepth(1);
        model.addAttribute("productCateDTOList", productCateDTOList);
        return "/admin/config/category";
    }

    @PostMapping("/admin/config/category")
    @ResponseBody
    public String AdminconfigCategoryPost(@RequestBody ProductCateDTO productCateDTO, @RequestParam String parent, @RequestParam int depth, Model model) {
        log.info("productCateDTO : "+productCateDTO);
        log.info("parent : "+parent);
        log.info("depth : "+depth);

        categoryService.insertProductCate(productCateDTO, parent, depth);
        return "success";
    }

}
