package com.lotte4.controller.pagecontroller.cs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerServiceController {

    @GetMapping("/cs/index")
    public String cs(){
        return "/cs/index";
    }

    @GetMapping("/cs/notice/list")
    public String notice(){
        return "/cs/notice/list";
    }

    @GetMapping("/cs/notice/view")
    public String noticeView(){
        return "/cs/notice/view";
    }

    @GetMapping("/cs/faq/list")
    public String faq(){
        return "/cs/faq/list";
    }

    @GetMapping("/cs/faq/view")
    public String faqView(){
        return "/cs/faq/view";
    }

    @GetMapping("/cs/qna/list")
    public String qna(){
        return "/cs/qna/list";
    }

    @GetMapping("/cs/qna/view")
    public String qnaView(){
        return "/cs/qna/view";
    }


}
