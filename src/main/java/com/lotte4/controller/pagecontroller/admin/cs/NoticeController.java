package com.lotte4.controller.pagecontroller.admin.cs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class NoticeController {
    
    //공지사항 목록
    @GetMapping("/admin/cs/notice/list")
    public String AdminNoticeList() {
        return "/admin/cs/notice/list";
    }

    //공지사항 보기
    @GetMapping("/admin/cs/notice/view")
    public String AdminNoticeView() {
        return "/admin/cs/notice/view";
    }
    
    //공지사항 작성
    @GetMapping("/admin/cs/notice/write")
    public String AdminNoticeWrite() {
        return "/admin/cs/notice/write";
    }

    //공지사항 수정
    @GetMapping("/admin/cs/notice/modify")
    public String AdminNoticeModify() {
        return "/admin/cs/notice/modify";
    }


}
