package com.lotte4.controller.pagecontroller.board;

import com.lotte4.dto.BoardCateDTO;
import com.lotte4.service.board.BoardCateService;
import com.lotte4.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    // boardCategory

    private final BoardCateService boardCateService;
    private final BoardService boardService;

    @GetMapping("/cs/qna/subcategories/{parentId}")
    @ResponseBody
    public List<BoardCateDTO> getSubCategories(@PathVariable int parentId) {
        return boardCateService.getSubCategories(parentId);
    }
    @GetMapping("/cs/qna/categories/{boardCateId}")
    public BoardCateDTO getCategories(@PathVariable int boardCateId) {
        return boardCateService.getCategories(boardCateId);
    }

}

