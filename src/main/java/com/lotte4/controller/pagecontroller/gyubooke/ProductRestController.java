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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@RestController
public class ProductRestController {

    private final ProductService productService;
    private final ObjectMapper objectMapper;
    private final CategoryService categoryService;

    Map<String, String> response = new HashMap<>();

    @GetMapping("/admin/product/register/{parentId}")
    public List<CateForProdRegisterDTO> registerCategory(@PathVariable int parentId) {
        return productService.getProductCateByParent(parentId);
    }


    @PostMapping(value = "/admin/product/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, Integer>> productRegisterCate(@RequestParam("cateId") int cateId,
                                                                    @RequestParam("img_1") MultipartFile img_1,
                                                                    @RequestParam("img_2") MultipartFile img_2,
                                                                    @RequestParam("img_3") MultipartFile img_3,
                                                                    @RequestParam("detail_") MultipartFile detail_,
                                                                    @RequestParam("optionsJson") String optionsJson,
                                                                    @ModelAttribute ProductDTO productDTO) {

        // 상품 카테고리 아이디 입력
        productDTO.setProductCateId(categoryService.getProductCate(cateId));

        // 'options' JSON 문자열을 Map<String, List<String>>으로 변환
        Map<String, List<String>> optionsMap = null;
        try {
            optionsMap = objectMapper.readValue(optionsJson,
                    new TypeReference<Map<String, List<String>>>() {
                    });
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

        ProductDTO dto = productService.insertProduct(productDTO);

        Map<String, Integer> response1 = new HashMap<>();

        if (dto != null) {
            int productId = dto.getProductId();
            response1.put("productId", productId);
            return ResponseEntity.ok().body(response1);
        }

        response1.put("productId", 0);
        return ResponseEntity.ok().body(response1);
    }

    @PostMapping("/admin/product/register/detail")
    public ResponseEntity<Map<String, String>> productRegisterCate(@RequestBody ProductDetailDTO productDetailDTO) {
        log.info(productDetailDTO);
        ProductDetailDTO dto = productService.insertProductDetail(productDetailDTO);

        if (dto != null) {
            response.put("status", "success");
            return ResponseEntity.ok().body(response);
        }

        response.put("status", "failure");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/admin/product/register/more")
    public void productRegisterMore(@RequestParam String prodONames,
                                    @RequestParam String prodPrices,
                                    @RequestParam String prodStocks,
                                    @RequestParam String mixedValuesList,
                                    @RequestParam String optionNames,
                                    @RequestParam String productId
    ) {
        productService.makeProductVariantDTOAndInsert(optionNames, prodONames, prodPrices, prodStocks, mixedValuesList, productId);

    }
}


