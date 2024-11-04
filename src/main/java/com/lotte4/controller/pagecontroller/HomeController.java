package com.lotte4.controller.pagecontroller;

import com.lotte4.dto.ProductCateDTO;
import com.lotte4.dto.ProductDTO;
import com.lotte4.dto.ProductListDTO;
import com.lotte4.service.CategoryService;
import com.lotte4.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/*

    2024.11.04 - 강중원 - 메인화면에 삽입되는 리스트 추가

 */

@Log4j2
@Controller
@AllArgsConstructor
public class HomeController {

    private final ProductService productService;

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {

        //히트상품 (조회수)
        List<ProductListDTO> HitList = productService.getProductWithType("Hit");
        model.addAttribute("HitList", HitList);

        //할인상품 (할인)
        List<ProductListDTO> DiscountList = productService.getProductWithType("Discount");
        model.addAttribute("DiscountList", DiscountList);

        //인기상품 (평점 많은순)
        List<ProductListDTO> ScoreManyList = productService.getProductWithType("ScoreMany");
        model.addAttribute("ScoreManyList", ScoreManyList);

        //최근상품 (등록일 최근순)
        List<ProductListDTO> RecentList = productService.getProductWithType("Recent");
        model.addAttribute("RecentList", RecentList);

        return "/index";
    }


}
