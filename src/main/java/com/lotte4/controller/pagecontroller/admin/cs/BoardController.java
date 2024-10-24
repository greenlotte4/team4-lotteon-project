package com.lotte4.controller.pagecontroller.admin.cs;

import com.lotte4.dto.BoardCateDTO;
import com.lotte4.dto.BoardCommentDTO;
import com.lotte4.dto.BoardRegisterDTO;
import com.lotte4.dto.BoardResponseDTO;
import com.lotte4.entity.Board;
import com.lotte4.service.board.BoardCateService;
import com.lotte4.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardCateService boardCateService;
    private final BoardService boardService;
    // 관리자 cs - Board (qna,faq,notice)

    // 글목록 - qna,fap
    @GetMapping("/admin/cs/{type}/list")
    public String AdminQnaList(Model model, @PathVariable String type) {
        if(Objects.equals(type, "notice")){
            List<BoardCateDTO> cate1 = boardCateService.selectBoardCatesByDepth(0);
            model.addAttribute("cates", cate1);
        }
        if(Objects.equals(type, "faq")) {
            List<BoardCateDTO> cate1 = boardCateService.selectBoardCatesByDepth(1);
            model.addAttribute("cate1", cate1);
        }
        List<BoardResponseDTO> boardList = boardService.selectAllBoardByType(type);
        model.addAttribute("boardList", boardList);

        return "/admin/cs/"+type+"/list";
    }
    // 글보기 - qna,fap
    @GetMapping("/admin/cs/{type}/view/{id}")
    public String adminQnaView(Model model, @PathVariable int id, @PathVariable String type) {
        model.addAttribute("board", boardService.selectBoardById(id));
        log.info("왜 view가 안보여?"+ boardService.selectBoardById(id));
        return "/admin/cs/"+type+"/view";
    }

    // faq 글쓰기
    @GetMapping("/admin/cs/faq/write")
    public String AdminCsFaqList(Model model){
        List<BoardCateDTO> cate1 = boardCateService.selectBoardCatesByDepth(1);
        model.addAttribute("cate1", cate1);
        return "/admin/cs/faq/write";
    }

    // faq 수정
    @GetMapping("/admin/cs/{type}/modify/{boardId}")
    public String AdminCsFaqModify(Model model,@PathVariable int boardId,@PathVariable String type) {

        if(Objects.equals(type, "notice")){
            List<BoardCateDTO> cate1 = boardCateService.selectBoardCatesByDepth(0);
            model.addAttribute("cates", cate1);
        }
        if(Objects.equals(type, "faq")) {
            List<BoardCateDTO> cate1 = boardCateService.selectBoardCatesByDepth(1);
            model.addAttribute("cate1", cate1);
        }
        BoardResponseDTO board = boardService.selectBoardById(boardId);

        model.addAttribute("board", board);

        return "/admin/cs/"+type+"/modify";
    }

    // 자주묻는질문. - 수정
    @PostMapping("/admin/cs/{type}/modify")
    public String AdminCsFaqModify(BoardRegisterDTO boardDTO, @PathVariable String type) {

        int boardId = boardDTO.getBoardId();
        Board savedBoard = boardService.updateBoard(boardDTO);

        return "redirect:/admin/cs/"+type+"/view/"+boardId;
    }

    // 답변쓰기 - qna (get, post)
    @GetMapping("/admin/cs/qna/reply/{id}")
    public String AdminQnaReply(Model model, @PathVariable int id) {
        model.addAttribute("board", boardService.selectBoardById(id));
        return "/admin/cs/qna/reply";
    }
    @PostMapping("/admin/cs/qna/reply/{id}")
    public String AdminQnaReply(BoardCommentDTO commentDTO, @PathVariable int id) {
        log.info("왜 댓글이 안되지"+commentDTO);
        commentDTO.setBoardId(id);
        boardService.insertBoardQnaComment(commentDTO);
        return "redirect:/admin/cs/qna/view/"+id;
    }
    @GetMapping("/cs/qna/subcategories/{parentId}")
    @ResponseBody
    public List<BoardCateDTO> getSubCategories(@PathVariable int parentId) {
        return boardCateService.getSubCategories(parentId);
    }
    @GetMapping("/cs/qna/categories/{boardCateId}")
    public BoardCateDTO getCategories(@PathVariable int boardCateId) {
        return boardCateService.getCategories(boardCateId);
    }


    // TODO : cate로 바꿔서 list 불러올 떄 동적 처리
//    @ResponseBody
//    @GetMapping("/admin/cs/board/list/{type}")
//    public ResponseEntity<List<BoardResponseDTO>> AdminQnaList(@PathVariable String type) {
//
//        return ResponseEntity.ok(boardService.selectAllBoardByType(type));
//    }

}
