package com.lotte4.controller.pagecontroller.CSB;

import com.lotte4.dto.*;
import com.lotte4.entity.ProductVariants;
import com.lotte4.repository.ProductVariantsRepository;
import com.lotte4.service.CartService;
import com.lotte4.service.OrderService;
import com.lotte4.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j2
@AllArgsConstructor
@Controller
public class OrderController {

    private final CartService cartService;
    private final ProductVariantsRepository productVariantsRepository;

    // 구매이전에는 세션이나 메모리에 잠시 보관하는것이 좋음.

    //장바구니
    @GetMapping("/product/order")
    public String CartBuyOrder(Model model, HttpSession session, Principal principal) {
        CartResponseDTO cartResponseDTO = (CartResponseDTO) session.getAttribute("directBuy"); // directBuy로 설정한 것만 조회
        List<CartItemDTO> cartItems = new ArrayList<>();

        // 로그인 체크
        if (principal == null) {
            return "redirect:/member/login";
        }

        String uid = principal.getName();

        // 공통 메서드를 통한 CartItemDTO 생성
        if (cartResponseDTO != null) {  // 바로 구매 시
            List<Integer> ids = cartResponseDTO.getProductVariants();
            List<Integer> counts = cartResponseDTO.getCounts();

            // 상품 구매 정보 조회 //일단 되는것으로 빨리 끝내고 추후 시간 남으면 바로 처리
            List<ProductVariants> productVariantsList = productVariantsRepository.findByVariantIdIn(ids);
            for (int i = 0; i < productVariantsList.size(); i++) {
                ProductVariants variant = productVariantsList.get(i);
                int count = counts.get(i);

                // 공통화된 CartItemDTO 생성
                CartItemDTO item = createCartItemDTO(variant, count);
                cartItems.add(item);
            }
        } else {  // 카트 구매 시
            List<Map<String, Object>> selectedCartItems = (List<Map<String, Object>>) session.getAttribute("selectedCartItems");

            if (selectedCartItems == null || selectedCartItems.isEmpty()) {
                return "redirect:/product/cart";
            }

            List<CartDTO> cartList = cartService.getCartItemsByIds(uid, selectedCartItems);
            for (CartDTO cart : cartList) {
                ProductVariants variant = cart.getProductVariants();

                // 공통화된 CartItemDTO 생성
                CartItemDTO item = createCartItemDTO(variant, cart.getCount());
                cartItems.add(item);
            }
            log.info("multiCart = {}", cartItems);
        }

        model.addAttribute("cartList", cartItems);
        session.setAttribute("selectedOrderItems", cartItems);
        log.info("test1231123" + cartItems);
        return "/product/order";
    }


    // 바로 구매하기
    @ResponseBody
    @PostMapping("/product/order")
    public ResponseEntity<String> CartBuyOrder(@RequestBody CartResponseDTO cartResponseDTO, HttpSession session) {
        if (cartResponseDTO != null) {
            session.setAttribute("directBuy", cartResponseDTO); // directBuy로만 설정
            log.info("send ResponseData " + cartResponseDTO);
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.ok("fail");
        }
    }

    @GetMapping("/product/complete")
    public String complete(HttpSession session, Model model) {
        session.getAttribute("selectedOrderItems");
        log.info("complete session"+ session.getAttribute("selectedOrderItems"));

        return "/product/complete";
    }


    
    //공통 메서드
    private CartItemDTO createCartItemDTO(ProductVariants variant, int count) {
        // Product 정보 설정
        ProductDTO productDTO = OrderDirectBuyDTO.fromEntity(variant.getProduct());
        ProductVariantsDTO productVariantsDTO = new ProductVariantsDTO();
        productVariantsDTO.setVariant_id(variant.getVariant_id());
        productVariantsDTO.setProduct(productDTO);

        // CartItemDTO 설정
        CartItemDTO item = new CartItemDTO();
        item.setVariantId(variant.getVariant_id());
        item.setSku(variant.getSku());
        item.setProductName(variant.getProduct().getName());
        item.setPrice(variant.getPrice());
        item.setCount(count);
        item.setStock(variant.getStock());
        item.setImg(variant.getProduct().getImg1());
        item.setDeliveryFee(variant.getProduct().getDeliveryFee());
        item.setProductVariants(productVariantsDTO);

        return item;
    }
}
