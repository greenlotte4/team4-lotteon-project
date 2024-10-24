package com.lotte4.controller.pagecontroller.gyubooke;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotte4.dto.CateForProdRegisterDTO;
import com.lotte4.dto.ProductDTO;
import com.lotte4.dto.ProductDetailDTO;
import com.lotte4.service.CategoryService;
import com.lotte4.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@RestController
public class ProductRestController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ObjectMapper objectMapper;

    @GetMapping("/admin/product/register/{parentId}")
    public List<CateForProdRegisterDTO> registerCategory(@PathVariable int parentId) {
        return productService.getProductCateByParent(parentId);
    }


    @PostMapping(value = "/admin/product/register", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void productRegisterCate(@RequestParam("img_1") MultipartFile img_1,
                                    @RequestParam("img_2") MultipartFile img_2,
                                    @RequestParam("img_3") MultipartFile img_3,
                                    @RequestParam("detail_") MultipartFile detail_,
                                    @RequestParam("optionsJson") String optionsJson,
                                    @ModelAttribute ProductDTO productDTO) {

        // 'options' JSON 문자열을 Map<String, List<String>>으로 변환
        Map<String, List<String>> optionsMap = null;
        try {
            optionsMap = objectMapper.readValue(optionsJson,
                    new TypeReference<Map<String, List<String>>>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        productDTO.setOptions(optionsMap);

        String img1 = productService.uploadProdImg(img_1);
        String img2 = productService.uploadProdImg(img_2);
        String img3 = productService.uploadProdImg(img_3);
        String detail = productService.uploadProdImg(detail_);

        productDTO.setImg1(img1);
        productDTO.setImg2(img2);
        productDTO.setImg3(img3);
        productDTO.setDetail(detail);

        productService.insertProduct(productDTO);
    }

    @PostMapping("/admin/product/register/detail")
    public void productRegisterCate(@RequestBody ProductDetailDTO productDetailDTO) {
        log.info(productDetailDTO);
        productService.insertProductDetail(productDetailDTO);
    }

}


