package com.lotte4.controller.pagecontroller.board;

import com.lotte4.dto.BoardCateDTO;
import com.lotte4.service.board.BoardCateService;
import com.lotte4.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardCateService boardCateService;
    private final BoardService boardService;

    // Category
    @GetMapping("/cs/qna/subcategories/{parentId}")
    public List<BoardCateDTO> getSubCategories(@PathVariable int parentId) {
        return boardCateService.getSubCategories(parentId);
    }
    @GetMapping("/cs/qna/categories/{boardCateId}")
    public BoardCateDTO getCategories(@PathVariable int boardCateId) {
        return boardCateService.getCategories(boardCateId);
    }
    @GetMapping("/cs/{type}/list")
    public String qna(Model model, @PathVariable String type) {

        model.addAttribute("boards", boardService.selectAllBoardByType(type));
        return "/cs/"+type+"/list";
    }

}

