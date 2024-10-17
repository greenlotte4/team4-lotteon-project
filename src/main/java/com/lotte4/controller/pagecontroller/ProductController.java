package com.lotte4.controller.pagecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/product/list")
    public String list(){
        return "/product/list";
    }

    @GetMapping("/product/list_grid")
    public String listgrid(){
        return "/product/list_grid";
    }

    @GetMapping("/product/cart")
    public String cart(){
        return "/product/cart";
    }

    @GetMapping("/product/complete")
    public String complete(){
        return "/product/complete";
    }

    @GetMapping("/product/order")
    public String order(){
        return "/product/order";
    }

    @GetMapping("/product/search")
    public String search(){
        return "/product/search";
    }

    @GetMapping("/product/view")
    public String view(){
        return "/product/view";
    }

}
