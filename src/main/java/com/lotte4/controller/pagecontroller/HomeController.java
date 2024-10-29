package com.lotte4.controller.pagecontroller;

import com.lotte4.dto.ProductCateDTO;
import com.lotte4.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Log4j2
@Controller
@AllArgsConstructor
public class HomeController {


    private final CategoryService categoryService;

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {



        return "/index";
    }


}
