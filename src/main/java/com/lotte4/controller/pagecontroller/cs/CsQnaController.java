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
/*
     이름 : 황수빈(최초 작성자)
     내용 : csQnaController 생성

     수정이력
      - 2025/10/29 황수빈 - 카테고리별로 다른 게시물 뿌리기 하는 중
      - 2025/10/27 김춘추 - OOO 메서드 수정
*/
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

    //공지사항 작성
    @GetMapping("/admin/cs/notice/write")
    public String AdminNoticeWrite(Model model) {
        model.addAttribute("cates",boardCateService.getSubCategories(8));
        return "/admin/cs/notice/write";
    }
    // 글목록 : qna, faq
    @GetMapping({"/cs/{type}/list", "/cs/{type}/list/{cate}"})  // 선택적인 cate 경로 처리
    public String qna(Model model,
                      @PathVariable String type,
                      @PathVariable(required = false) Integer cate,// cate가 없으면 null로 처리
                      @RequestParam(defaultValue = "0") int page,
                      @RequestParam(defaultValue = "8") int size) {

        Page<BoardResponseDTO> boardList;

        if (cate == null) {
            // cate가 없거나 null일 때 전체 보드 가져오기
            boardList = boardService.selectAllBoardByType(type, page,  size);
        } else {
            // cate가 있을 때 해당 카테고리에 맞는 보드 가져오기
            boardList = boardService.selectAllBoardByCateId(cate, type, page, size);
        }

        model.addAttribute("boards", boardList.getContent());
        model.addAttribute("totalPages", boardList.getTotalPages());
        model.addAttribute("currentPage", page);

        return "/cs/" + type + "/list";
    }



    // 글보기 : qna, faq
    @GetMapping("/cs/{type}/view/{id}")
    public String qnaView(Model model, @PathVariable String type, @PathVariable int id) {
        model.addAttribute("board", boardService.selectBoardById(id));
        log.info("어떻게 오나 함 보자"+boardService.selectBoardById(id));
        return "/cs/"+type+"/view";
    }


}

