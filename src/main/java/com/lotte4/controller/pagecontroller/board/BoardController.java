package com.lotte4.controller.pagecontroller.board;

import com.lotte4.dto.BoardCateDTO;
import com.lotte4.entity.BoardCate;
import com.lotte4.repository.BoardCateRepository;
import com.lotte4.service.BoardCateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardCateService boardCateService;

    @GetMapping("/cs/qna/subcategories/{parentId}")
    public List<BoardCateDTO> getSubCategories(@PathVariable int parentId) {
        return boardCateService.getSubCategories(parentId);
    }
}

