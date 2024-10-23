package com.lotte4.controller.pagecontroller.admin.cs;

import com.lotte4.dto.BoardCateDTO;
import com.lotte4.dto.BoardCommentDTO;
import com.lotte4.dto.BoardRegisterDTO;
import com.lotte4.dto.BoardResponseDTO;
import com.lotte4.repository.board.BoardRepository;
import com.lotte4.service.board.BoardCateService;
import com.lotte4.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Controller
public class QnaController {
    private final BoardCateService boardCateService;
    private final BoardService boardService;
    // 관리자 문의하기 목록
    @GetMapping("/admin/cs/{type}/list")
    public String AdminQnaList(Model model, @PathVariable String type) {
        model.addAttribute("boardList", boardService.selectAllBoardByType(type));
        List<BoardCateDTO> cate1 = boardCateService.selectBoardCatesByDepth(1);
        model.addAttribute("cate1", cate1);
        return "/admin/cs/qna/list";
    }
    @ResponseBody
    @GetMapping("/admin/cs/board/list/{type}")
    public ResponseEntity<List<BoardResponseDTO>> AdminQnaList(@PathVariable String type) {

        return ResponseEntity.ok(boardService.selectAllBoardByType(type));
    }

    //문의하기 답변
    @GetMapping("/admin/cs/qna/reply/{id}")
    public String AdminQnaReply(Model model, @PathVariable int id) {
        model.addAttribute("board", boardService.selectBoardById(id));
        return "/admin/cs/qna/reply";
    }
    //문의하기 답변
    @PostMapping("/admin/cs/qna/reply/{id}")
    public String AdminQnaReply(BoardCommentDTO commentDTO, @PathVariable int id) {
        commentDTO.setBoardId(id);
        boardService.insertBoardQnaComment(commentDTO);
        return "redirect:/admin/cs/qna/view/"+id;
    }

    
    //문의하기 보기
    @GetMapping("/admin/cs/qna/view/{id}")
    public String adminQnaView(Model model, @PathVariable int id) {
        model.addAttribute("board", boardService.selectBoardById(id));
        return "/admin/cs/qna/view";
    }
}
