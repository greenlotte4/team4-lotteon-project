package com.lotte4.controller.pagecontroller.board;

import com.lotte4.dto.BoardCateDTO;
import com.lotte4.service.board.BoardCateService;
import com.lotte4.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardCateService boardCateService;
    private final BoardService boardService;

    // /cs/qna/write에 category
    @GetMapping("/cs/qna/subcategories/{parentId}")
    public List<BoardCateDTO> getSubCategories(@PathVariable int parentId) {
        return boardCateService.getSubCategories(parentId);
    }

}

