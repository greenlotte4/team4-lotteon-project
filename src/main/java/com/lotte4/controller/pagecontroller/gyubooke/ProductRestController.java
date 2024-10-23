package com.lotte4.controller.pagecontroller.gyubooke;

import com.lotte4.dto.CateForProdRegisterDTO;
import com.lotte4.dto.ProductDTO;
import com.lotte4.dto.ProductDetailDTO;
import com.lotte4.service.CategoryService;
import com.lotte4.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
public class ProductRestController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/admin/product/register/{parentId}")
    public List<CateForProdRegisterDTO> registerCategory(@PathVariable int parentId) {
         return productService.getProductCateByParent(parentId);
    }

    @PostMapping("/admin/product/register")
    public void productRegisterCate(@RequestBody ProductDTO productDTO) {
        log.info(productDTO);
    }

    @PostMapping("/admin/product/register/detail")
    public void productRegisterCate(@RequestBody ProductDetailDTO productDetailDTO) {
        log.info(productDetailDTO);
    }



}
