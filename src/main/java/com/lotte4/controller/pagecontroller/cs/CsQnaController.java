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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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

    @PostMapping("/cs/{type}/write")
    public String qnaWrite(BoardRegisterDTO dto, HttpServletRequest req, @PathVariable String type) {

        dto.setRegIp(req.getRemoteAddr());
        boardService.insertBoardQna(dto);

        if(Objects.equals(type, "faq")){
            return "redirect:/admin/cs/faq/list";
        }
        return "redirect:/cs/" +type+ "/list";

    }

    @GetMapping("/cs/{type}/list")
    public String qna(Model model, @PathVariable String type) {

        model.addAttribute("boards", boardService.selectAllBoardByType(type));
        return "/cs/"+type+"/list";
    }


    @GetMapping("/cs/qna/view/{id}")
    public String qnaView(Model model, @PathVariable int id) {

        model.addAttribute("board", boardService.selectBoardById(id));
        return "/cs/qna/view";
    }

}

