package com.lotte4.controller.pagecontroller.cs;

import com.lotte4.dto.BoardCateDTO;
import com.lotte4.dto.BoardRegisterDTO;
import com.lotte4.dto.BoardResponseDTO;
import com.lotte4.entity.Board;
import com.lotte4.service.UserService;
import com.lotte4.service.board.BoardCateService;
import com.lotte4.service.board.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
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

    // qna 글쓰기
    @GetMapping("/cs/qna/write")
    public String qnaWrite(Model model){
        List<BoardCateDTO> cate1 = boardCateService.selectBoardCatesByDepth(1);
        model.addAttribute("cate1", cate1);
        return "/cs/qna/write";
    }

    @PostMapping("/cs/{type}/write")
    public String qnaWrite(BoardRegisterDTO dto, HttpServletRequest req, @PathVariable String type) {

        dto.setRegIp(req.getRemoteAddr());
        Board savedBoard = boardService.insertBoard(dto);
        if(savedBoard!=null){
        if(Objects.equals(type, "qna")){
            return "redirect:/cs/qna/list";
        }
        if(Objects.equals(type, "faq")){
            return "redirect:/admin/cs/faq/list";
        }
        if(Objects.equals(type, "notice")){
            return "redirect:/admin/cs/notice/list";
        }
        }
        return null;

    }

    // 글목록 : qna, faq
    @GetMapping("/cs/{type}/list")
    public String qna(Model model, @PathVariable String type,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "8") int size){
        Page<BoardResponseDTO> boardList = boardService.selectAllBoardByType(type, page, size);
        model.addAttribute("boards", boardList.getContent());
        model.addAttribute("totalPages", boardList.getTotalPages());
        model.addAttribute("currentPage", page);
        return "/cs/"+type+"/list";
    }


    // 글보기 : qna, faq
    @GetMapping("/cs/qna/view/{id}")
    public String qnaView(Model model, @PathVariable int id) {
        model.addAttribute("board", boardService.selectBoardById(id));
        return "/cs/qna/view";
    }


}

