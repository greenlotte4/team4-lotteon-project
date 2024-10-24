package com.lotte4.controller.pagecontroller.admin.product;

import com.lotte4.dto.ProductCateDTO;
import com.lotte4.dto.ProductDTO;
import com.lotte4.service.CategoryService;
import com.lotte4.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class AdminProductController {

    private final CategoryService categoryService;
    private final ProductService productService;

    // 상품현황
    @GetMapping("/admin/product/list")
    public String AdminProductList(Model model) {
        List<ProductDTO> products = productService.getAllProducts();
        model.addAttribute("products", products);

        return "/admin/product/list";
    }

    // 상품등록
    @GetMapping("/admin/product/register")
    public String AdminProductRegister(Model model) {
        model.addAttribute("productCate1List", categoryService.getProductCateListWithDepth(1));
        return "/admin/product/register";
    }

    // 상품 상세 등록
    @GetMapping("/admin/product/registerMore")
    public String AdminProductRegisterMore(int productId, Model model) {

        ProductDTO productDTO = productService.getProductById(productId);
        int prodId = productDTO.getProductId();
        Map<String, List<String>> options = productDTO.getOptions();

        model.addAttribute("prodId", prodId);
        model.addAttribute("options", options);

        return "/admin/product/registerMore";
    }


    // 상품수정
    @GetMapping("/admin/product/modify")
    public String AdminProductModify(int productId, Model model) {

        ProductDTO productDTO = productService.getProductById(productId);
        int productCateId = productDTO.getProductCate_productCateId();
        model.addAttribute("productDTO", productDTO);

        ProductCateDTO productCateDTO = categoryService.getProductCate(productCateId);
        ProductCateDTO parentProductCateDTO = productCateDTO.getParent();
        int cateDepth = productCateDTO.getDepth();

        if (cateDepth == 2) {
            model.addAttribute("productCate", productCateDTO);
        } else if (cateDepth == 3) {
            model.addAttribute("productCate", productCateDTO);
            model.addAttribute("parentProductCate", parentProductCateDTO);
        }

        return "/admin/product/modify";
    }






}
