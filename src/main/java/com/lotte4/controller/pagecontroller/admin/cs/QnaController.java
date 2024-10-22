package com.lotte4.controller.pagecontroller.admin.cs;

import com.lotte4.dto.BoardResponseDTO;
import com.lotte4.repository.board.BoardRepository;
import com.lotte4.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Controller
public class QnaController {

    private final BoardService boardService;
    //문의하기 목록
    @GetMapping("/admin/cs/{type}/list")
    public String AdminQnaList(Model model, @PathVariable String type) {
        model.addAttribute("boardList", boardService.selectAllBoardByType(type));
        return "/admin/cs/qna/list";
    }
    @ResponseBody
    @GetMapping("/admin/cs/board/list/{type}")
    public ResponseEntity<List<BoardResponseDTO>> AdminQnaList(@PathVariable String type) {

        return ResponseEntity.ok(boardService.selectAllBoardByType(type));
    }
    //문의하기 답변
    @GetMapping("/admin/cs/qna/reply")
    public String AdminQnaReply() {
        return "/admin/cs/qna/reply";
    }
    
    //문의하기 보기
    @GetMapping("/admin/cs/qna/view")
    public String AdminQnaView() {
        return "/admin/cs/qna/view";
    }
}
