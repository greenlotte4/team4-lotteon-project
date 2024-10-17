 package com.lotte4.controller.pagecontroller.admin.cs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class FaqController {
    
    //공지사항 목록
    @GetMapping("/admin/cs/faq/list")
    public String AdminCsFaqList() {
        return "/admin/cs/faq/list";
    }

    //공지사항 보기
    @GetMapping("/admin/cs/faq/view")
    public String AdminCsFaqView() {
        return "/admin/cs/faq/view";
    }
    
    //공지사항 작성
    @GetMapping("/admin/cs/faq/write")
    public String AdminCsFaqWrite() {
        return "/admin/cs/faq/write";
    }

    //공지사항 수정
    @GetMapping("/admin/cs/faq/modify")
    public String AdminCsFaqModify() {
        return "/admin/cs/faq/modify";
    }


}
