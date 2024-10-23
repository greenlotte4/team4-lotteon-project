package com.lotte4.controller.pagecontroller.CSB;

import com.lotte4.dto.ProductDTO;
import com.lotte4.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@AllArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/product/order")
    public String viewOrder(Model model) {
        ProductDTO productDTO = new ProductDTO();
        int no = 3;
        ProductDTO getproductDTO = orderService.selectByProduct(no);
        log.info("product = " + getproductDTO);
      if (getproductDTO != null) {
        model.addAttribute("product", getproductDTO);
        log.info("product : " + productDTO);
    } else {
        log.warn("Product not found");
    }
        return "/product/order";
    }

    @GetMapping("/product/complete")
    public String complete(){
        return "/product/complete";
    }



}
