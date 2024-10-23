 package com.lotte4.controller.pagecontroller.admin.cs;

import com.lotte4.dto.BoardCateDTO;
import com.lotte4.service.board.BoardCateService;
import com.lotte4.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@RequiredArgsConstructor
 @Controller
public class FaqController {

    private final BoardService boardService;
    private final BoardCateService boardCateService;

    //공지사항 글쓰기
    @GetMapping("/admin/cs/faq/write")
    public String AdminCsFaqList(Model model){

        List<BoardCateDTO> cate1 = boardCateService.selectBoardCatesByDepth(1);
        model.addAttribute("cate1", cate1);

        return "/admin/cs/faq/write";

    }

    //공지사항 수정
    @GetMapping("/admin/cs/faq/modify")
    public String AdminCsFaqModify() {
        return "/admin/cs/faq/modify";
    }


}
