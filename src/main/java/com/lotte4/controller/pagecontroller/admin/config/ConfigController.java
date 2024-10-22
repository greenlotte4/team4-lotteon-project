package com.lotte4.controller.pagecontroller.admin.config;


import com.lotte4.dto.admin.config.InfoDTO;
import com.lotte4.dto.ProductCateDTO;
import com.lotte4.entity.ProductCate;
import com.lotte4.service.CategoryService;
import lombok.extern.log4j.Log4j2;

import com.lotte4.dto.admin.config.VersionDTO;
import com.lotte4.entity.Info;
import com.lotte4.service.admin.config.InfoService;
import com.lotte4.service.admin.config.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Controller
public class ConfigController {
    // View반환 Controller
    private final VersionService versionService;
    private final InfoService infoService;

    @GetMapping("/admin/config/info")
    public String AdminConfigInfoInsert(Model model) {

        InfoDTO infoDTO = infoService.selectInfoDTO();
        model.addAttribute("info", infoDTO);

        return "/admin/config/info";
    }

    private final CategoryService categoryService;

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
    public String AdminconfigVersion(Model model) {
        List<VersionDTO> versions = versionService.selectAll();
        model.addAttribute("versions", versions);
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
    public String AdminConfigCategoryPost(@RequestBody ProductCateDTO productCateDTO, @RequestParam String parent, @RequestParam int depth, Model model) {
        log.info("productCateDTO : "+productCateDTO);
        log.info("parent : "+parent);
        log.info("depth : "+depth);

        categoryService.insertProductCate(productCateDTO, parent, depth);
        return "success";
    }

    @DeleteMapping("/admin/config/category")
    @ResponseBody
    public String AdminConfigCategoryDelete(@RequestBody Map<String, String> requestBody) {
        String name = requestBody.get("name");

        log.info("name : "+name);
        boolean value = categoryService.deleteProductCate(name);
        if(value){
            return "success";
        }
        else{
            return "fail";
        }
    }

}
