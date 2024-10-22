package com.lotte4.controller.pagecontroller.cs;

import com.lotte4.dto.BoardCateDTO;
import com.lotte4.helper.BoardCategory;
import com.lotte4.service.BoardCateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Controller
public class CsQnaController {

    private final BoardCateService boardCateService;

    @GetMapping("/cs/qna/write")
    public String qnaWrite(Model model){
        List<BoardCateDTO> cate1 = boardCateService.selectBoardCatesByDepth(1);
        model.addAttribute("cate1", cate1);

        return "/cs/qna/write";
    }

    }

