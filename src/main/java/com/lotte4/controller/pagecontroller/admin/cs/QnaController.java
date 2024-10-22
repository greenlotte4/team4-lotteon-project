package com.lotte4.controller.pagecontroller.admin.cs;

import com.lotte4.helper.BoardCategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;


@Controller
public class QnaController {
    //문의하기 목록
    @GetMapping("/admin/cs/qna/list")
    public String AdminQnaList() {
        return "/admin/cs/qna/list";
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
