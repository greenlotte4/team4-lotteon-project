package com.lotte4.controller.pagecontroller;

import com.lotte4.dto.CartDTO;
import com.lotte4.dto.ProductDTO;
import com.lotte4.dto.ProductVariantsDTO;
import com.lotte4.dto.Product_V_DTO;
import com.lotte4.service.CartService;
import com.lotte4.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


//날짜 : 2024/10/
//이름 : (최초 작성자)
//내용 : OOO 생성
//
//수정이력
//      - 2025/10/28 강중원 - 리스트 불러오기 임시 기능 메서드 추가


@Log4j2
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final CartService cartService;
    private final ProductService productService;


    @GetMapping("/product/list")
    public String list(Model model) {
        List<ProductDTO> productDTOList = productService.getAllProducts();
        model.addAttribute("productDTOList", productDTOList);
        log.info(productDTOList);
        List<String> cate = new ArrayList<>();
        cate.add("전체상품");
        model.addAttribute("categories", cate);
        return "/product/list";
    }

    @GetMapping("/product/list/{cate}")
    public String listWithCate(@PathVariable int cate, Model model) {
        List<ProductDTO> productDTOList = productService.getProductWithCate(cate);
        model.addAttribute("productDTOList", productDTOList);
        List<String> cateList = new ArrayList<>();

        model.addAttribute("categories", cateList);
        log.info(productDTOList);
        return "/product/list";
    }

    @GetMapping("/product/list_grid")
    public String listgrid(){
        return "/product/list_grid";
    }

    @GetMapping("/product/cart")
    public String cart(Model model, Principal principal) {

        if (principal == null || principal.getName() == null) {
            return "redirect:/member/login"; // 로그인 체크
        }
        String uid = principal.getName(); // principal에서 uid 가져오기

        List<CartDTO> cartList = cartService.getCartByUserId(uid);

        model.addAttribute("cartList", cartList);

        log.info("cartList : " + cartList);

        return "/product/cart";
    }


    @PostMapping("/product/cart/delete")
    @ResponseBody
    public ResponseEntity<Void> deleteCartItems(@RequestBody Map<String, List<Integer>> requestBody) {

        List<Integer> cartItems = requestBody.get("cartItems");

        log.info("cartItems : " + cartItems);

        // cartItems가 null이거나 비어 있을 경우 처리
        if(cartItems == null || cartItems.isEmpty()) {
            return ResponseEntity.badRequest().build(); // 잘못된 요청 처리
        }

        for (int cartId : cartItems) {
            cartService.deleteCartItems(cartId);
        }

        return ResponseEntity.ok().build();
    }



    @GetMapping("/product/search")
    public String search(){
        return "/product/search";
    }

    @GetMapping("/product/view")
    public String view(int productId, Model model) {

        Product_V_DTO productDTO = productService.getProductById2(productId);
        log.info("productDTO : " + productDTO);
        model.addAttribute("productDTO", productDTO);

        return "/product/view";
    }

}
