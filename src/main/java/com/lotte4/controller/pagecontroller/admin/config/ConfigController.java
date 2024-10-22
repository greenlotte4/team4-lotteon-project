package com.lotte4.controller.pagecontroller.admin.config;

import com.lotte4.dto.BannerDTO;
import com.lotte4.dto.admin.config.InfoDTO;
import com.lotte4.dto.ProductCateDTO;
import com.lotte4.entity.ProductCate;
import com.lotte4.service.CategoryService;
import com.lotte4.service.admin.config.BannerService;
import lombok.extern.log4j.Log4j2;

import com.lotte4.dto.admin.config.VersionDTO;
import com.lotte4.entity.Info;
import com.lotte4.service.admin.config.InfoService;
import com.lotte4.service.admin.config.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final BannerService bannerService;
    //배너관리
    @GetMapping("/admin/config/banner")
    public String AdminconfigBanner(Model model) {
        //기본 배너관리 페이지로 이동
        List<BannerDTO> bannerDTOS = bannerService.getBannersByLocation("MAIN1");
        model.addAttribute("configBanners", bannerDTOS);

        return "/admin/config/banner";
    }

    @GetMapping("/admin/config/banner/{location}")
    public String AdminconfigBannerWithLocation(@PathVariable String location, Model model) {
        List<BannerDTO> bannerDTOS = bannerService.getBannersByLocation(location);
        model.addAttribute("configBanners", bannerDTOS);
        model.addAttribute("locationNow", location);
        return "/admin/config/banner";
    }

    @PostMapping(value = "/admin/config/banner", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> AdminconfigBannerWithLocation(
            @RequestParam("bannerImg") MultipartFile bannerImg,
            @ModelAttribute BannerDTO bannerDTO, Model model) {

        try {
            String result = bannerService.insertBanner(bannerDTO,bannerImg);
            if(result.equals("success")) {
                return ResponseEntity.ok("success");
            }else{
                return ResponseEntity.ok("fail");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok("fail");
        }
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
