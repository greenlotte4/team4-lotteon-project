package com.lotte4.controller.pagecontroller.admin.config;

import com.lotte4.dto.admin.config.InfoDTO;
import com.lotte4.dto.admin.config.VersionDTO;
import com.lotte4.entity.Info;
import com.lotte4.service.admin.config.InfoService;
import com.lotte4.service.admin.config.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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
    public String AdminConfigCategory() {
        return "/admin/config/category";
    }

}
