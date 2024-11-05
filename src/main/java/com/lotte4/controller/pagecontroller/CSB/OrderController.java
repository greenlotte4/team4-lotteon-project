package com.lotte4.controller.pagecontroller.CSB;

import com.lotte4.dto.*;
import com.lotte4.service.CartService;
import com.lotte4.service.OrderService;
import com.lotte4.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@AllArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    // 구매이전에는 세션이나 메모리에 잠시 보관하는것이 좋음.

    //장바구니
    @GetMapping("/product/order")
    public String CartBuyOrder(Model model, HttpSession session, Principal principal) {
        List<Integer> selectedCartIds = (List<Integer>) session.getAttribute("selectedCartIds");

        // 선택된 항목이 없을 경우 장바구니로 리다이렉트(선택해서 오지 않으면 다시 리턴)
        if (selectedCartIds == null || selectedCartIds.isEmpty()) {
            return "redirect:/product/cart";
        }
        String uid = principal.getName();
        List<CartDTO> cartList = cartService.getCartItemsByIds(uid, selectedCartIds);
        model.addAttribute("cartList", cartList);
        model.addAttribute("isSingleProduct", false);

        return "/product/order";
    }

    
    // 바로 구매하기
    @ResponseBody
    @PostMapping("/product/order")
    public String CartBuyOrder(@RequestBody CartResponseDTO cartResponseDTO) {

        log.info("cartResponseDTO: " + cartResponseDTO);

        //        CartDTO singleCartItem = orderService.selectByProductAsCartDTO(variant_id, count);
//        List<CartDTO> cartList = new ArrayList<>();
//        cartList.add(singleCartItem);
//        model.addAttribute("cartList", cartList);
//        model.addAttribute("isSingleProduct", true);

        return "/product/order";
    }

    @GetMapping("/product/complete")
    public String complete() {
        return "/product/complete";
    }

}
