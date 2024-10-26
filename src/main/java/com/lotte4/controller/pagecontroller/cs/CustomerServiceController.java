package com.lotte4.controller.pagecontroller.cs;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CustomerServiceController {

    @GetMapping("/cs/index")
    public String cs(){
        return "/cs/index";
    }


    @GetMapping("/cs/notice/view")
    public String noticeView(){
        return "/cs/notice/view";
    }

    @GetMapping("/cs/faq/view")
    public String faqView(){
        return "/cs/faq/view";
    }



    @GetMapping("/cs/qna/view")
    public String qnaView(){
        return "/cs/qna/view";
    }


}
