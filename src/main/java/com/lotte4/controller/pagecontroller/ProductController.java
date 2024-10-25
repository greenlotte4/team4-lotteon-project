package com.lotte4.controller.pagecontroller;

import com.lotte4.dto.CartDTO;
import com.lotte4.service.CartService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log4j2
@Controller
public class ProductController {

    private final CartService cartService;

    public ProductController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/product/list")
    public String list(){
        return "/product/list";
    }

    @GetMapping("/product/list_grid")
    public String listgrid(){
        return "/product/list_grid";
    }

    @GetMapping("/product/cart/{user_id}")
    public String cart(Model model, @PathVariable("user_id") int user_id) {

        List<CartDTO> cartList = cartService.getCartByUserId(user_id);

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
    public String view(){
        return "/product/view";
    }

}
