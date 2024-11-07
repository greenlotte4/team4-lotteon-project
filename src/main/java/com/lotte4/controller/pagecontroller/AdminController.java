package com.lotte4.controller.pagecontroller;

import com.lotte4.service.VisitorService;
import com.querydsl.core.types.Visitor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
/*

    2024.11.07 어제, 오늘 방문자수 가져오는 메서드 추가

 */


@Log4j2
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final VisitorService visitorService;

    @GetMapping("/admin/index")
    public String AdminHome(Model model) {

        int yesterdayVisitor = visitorService.getVisitorCountYesterday();      //어제 방문자수
        int todayVisitor = visitorService.getVisitorCountToday();          //오늘 방문자수
        int totalVisitor = visitorService.getTotalVisitorCount();

        //방문자수
        model.addAttribute("yesterdayVisitor", yesterdayVisitor);
        model.addAttribute("todayVisitor", todayVisitor);
        model.addAttribute("totalVisitor", totalVisitor);

        return "/admin/index";
    }

}
