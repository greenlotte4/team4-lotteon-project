package com.lotte4.controller.pagecontroller.cs;

import com.lotte4.dto.BoardCateDTO;
import com.lotte4.dto.BoardRegisterDTO;
import com.lotte4.entity.Board;
import com.lotte4.service.UserService;
import com.lotte4.service.board.BoardCateService;
import com.lotte4.service.board.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
public class  CsQnaController {
    private final UserService userService;
    private final BoardCateService boardCateService;
    private final BoardService boardService;

    @GetMapping("/cs/qna/write")
    public String qnaWrite(Model model){
        List<BoardCateDTO> cate1 = boardCateService.selectBoardCatesByDepth(1);
        model.addAttribute("cate1", cate1);

        return "/cs/qna/write";
    }

    @PostMapping("/cs/qna/write")
    public String qnaWrite(BoardRegisterDTO dto, HttpServletRequest req) {
        dto.setRegIp(req.getRemoteAddr());
        boardService.insertBoardQna(dto);
        return "redirect:/cs/qna/list";
    }
    }

