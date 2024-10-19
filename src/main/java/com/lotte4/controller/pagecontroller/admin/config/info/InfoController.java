package com.lotte4.controller.pagecontroller.admin.config.info;

import com.lotte4.dto.InfoDTO;
import com.lotte4.entity.Info;
import com.lotte4.service.InfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequiredArgsConstructor
public class InfoController {

    private final InfoService infoService;
    //기본설정
    @GetMapping("/admin/config/info")
    public String AdminConfigInfoInsert(Model model) {

        InfoDTO infoDTO = infoService.selectInfoDTO();
        model.addAttribute("info", infoDTO);

        return "/admin/config/info";
    }
    // 제목과 부제 수정
    @PostMapping("/admin/config/info")
    public ResponseEntity<InfoDTO> updateInfo(@RequestBody InfoDTO infoDto) {

        InfoDTO updatedInfo = infoService.updateInfoTitleAndSubtitle(infoDto);
        return ResponseEntity.ok(updatedInfo);
    }

    // 기업 정보 수정
    @PostMapping("/admin/config/company")
    public ResponseEntity<InfoDTO> updateCompanyInfo(@RequestBody InfoDTO infoDto) {

        InfoDTO updatedInfo = infoService.updateCompanyInfo(infoDto);
        return ResponseEntity.ok(updatedInfo);
    }

    // 고객센터 정보 수정
    @PostMapping("/admin/config/cs")
    public ResponseEntity<InfoDTO> updateCompanyCs(@RequestBody InfoDTO infoDto) {

        InfoDTO updatedInfo = infoService.updateCompanyCs(infoDto);
        return ResponseEntity.ok(updatedInfo);
    }

    // 카피라이트 정보 수정
    @PostMapping("/admin/config/copyright")
    public ResponseEntity<InfoDTO> updateCopyright(@RequestBody InfoDTO infoDto) {

        InfoDTO updatedInfo = infoService.updateCopyright(infoDto);
        return ResponseEntity.ok(updatedInfo);
    }
}

