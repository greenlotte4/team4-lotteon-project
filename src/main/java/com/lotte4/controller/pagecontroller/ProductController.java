package com.lotte4.controller.pagecontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotte4.dto.*;
import com.lotte4.entity.Cart;
import com.lotte4.entity.ProductVariants;
import com.lotte4.entity.User;
import com.lotte4.service.CartService;
import com.lotte4.service.ProductService;
import com.lotte4.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


//날짜 : 2024/10/
//이름 : (최초 작성자)
//내용 : OOO 생성
//
//수정이력
//      - 2024/10/28 강중원 - 리스트 불러오기 임시 기능 메서드 추가
//      - 2024/10/30 강은경 - 장바구니 insert하는 postmapping추가
//      - 2024/10/31 조수빈 - session 정보 전달하는 HttpSession 추가
//      - 2024/11/04 강중원 - 상품 카테고리와 타입에 따른 정렬 매핑 추가

@Log4j2
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final CartService cartService;
    private final ProductService productService;
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/product/list")
    public String list(Model model) {
        //home - 모든 상품
        List<ProductListDTO> productDTOList = productService.getProductWithCateAndType(0, "sold");
        model.addAttribute("productDTOList", productDTOList);
        log.info(productDTOList);
        List<String> categories = new ArrayList<>();
        model.addAttribute("categories", categories);
        model.addAttribute("cate", 0);
        return "/product/list";
    }

    @GetMapping("/product/list/{cate}")
    public String listWithCate(@PathVariable int cate, Model model) {
        //List<ProductDTO> productDTOList = productService.getProductWithCate(cate);
        //기본 정렬 - 판매량
        List<ProductListDTO> productDTOList = productService.getProductWithCateAndType(cate, "sold");
        model.addAttribute("productDTOList", productDTOList);

        List<ProductCateDTO> cateList = productService.getProductCates(cate);

        model.addAttribute("categories", cateList);

        model.addAttribute("cate", cate);

        log.info(productDTOList);
        return "/product/list";
    }

    @GetMapping("/product/list/{cate}/{type}")
    public String listWithCateAndType(@PathVariable int cate,@PathVariable String type, Model model) {
        List<ProductListDTO> productDTOList = productService.getProductWithCateAndType(cate, type);
        model.addAttribute("productDTOList", productDTOList);

        List<ProductCateDTO> cateList = productService.getProductCates(cate);

        model.addAttribute("categories", cateList);
        log.info(productDTOList);
        return "/product/list";
    }

    @GetMapping("/product/list_grid")
    public String listgrid() {
        return "/product/list_grid";
    }

    // cart list
    @GetMapping("/product/cart")
    public String cart(Model model, Principal principal, HttpSession session) {

        if (principal == null || principal.getName() == null) {
            return "redirect:/member/login"; // 로그인 체크
        }
        String uid = principal.getName(); // principal에서 uid 가져오기
        log.info("uid : " + uid);

        List<CartDTO> cartList = cartService.getCartByUserId(uid);

        session.setAttribute("secartList", cartList);
        log.info("session cartList"+cartList);

        model.addAttribute("cartList", cartList);

        log.info("cartList : " + cartList);

        return "/product/cart";
    }

    // 장바구니 insert
    @ResponseBody
    @PostMapping("/product/cart")
    public ResponseEntity<String> addCart(@RequestBody CartResponseDTO cartResponseDTO, Principal principal) {

        // 로그인 안 했으면 로그인 페이지로 리다이렉트
        if (principal == null) {
            return ResponseEntity.ok("noUser");
        }

        log.info("cartResponseDTO : " + cartResponseDTO);
        log.info("cartResponseDTO : " + cartResponseDTO.getProductVariants().get(0));


        String username = principal.getName();
        User user = userService.findByUid(username);

        log.info("user : " + user);

        // principal user정보 set
        cartResponseDTO.setUser(user);

        List<Cart> savedCart = cartService.insertCart(cartResponseDTO);
        log.info("savedCart : " + savedCart);
        if(savedCart != null){
            return ResponseEntity.ok("success");

        }else {
            return ResponseEntity.ok("failed");
        }
    }


    
    // 선택구매용
    @PostMapping("/product/cart/selected")
    public ResponseEntity<?> storeSelectedCartItems(@RequestBody List<Map<String, Object>> selectedItems, HttpSession session) {
        log.info("selectedItems : " + selectedItems);

        // Map을 풀어서 각 cartId와 count에 접근하여 업데이트 수행
        selectedItems.forEach(item -> {
            Integer cartId = (Integer) item.get("cartId");
            Integer count = (Integer) item.get("count");
            cartService.updateCartItem(cartId, count);  // update 메서드 호출
        });
        session.setAttribute("selectedCartItems", selectedItems);
        return ResponseEntity.ok().build();
    }





    @PostMapping("/product/cart/delete")
    @ResponseBody
    public ResponseEntity<Void> deleteCartItems(@RequestBody Map<String, List<Integer>> requestBody) {

        List<Integer> cartItems = requestBody.get("cartItems");

        log.info("cartItems : " + cartItems);

        // cartItems가 null이거나 비어 있을 경우 처리
        if (cartItems == null || cartItems.isEmpty()) {
            return ResponseEntity.badRequest().build(); // 잘못된 요청 처리
        }

        for (int cartId : cartItems) {
            cartService.deleteCartItems(cartId);
        }

        return ResponseEntity.ok().build();
    }

    //2024.11.05 강중원 - 검색기능
    @GetMapping("/product/search/{keyword}")
    public String search(
            @PathVariable String keyword,
            @RequestParam(required = false) List<String> filters,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            Model model){
        log.info("keyword : " + keyword);
        if (filters == null) {
            filters = new ArrayList<>();
            filters.add("prodName");
        }
        if(minPrice == null || !filters.contains("price")){
            minPrice = 0;
        }
        if(maxPrice == null || !filters.contains("price")){
            maxPrice = 0;
        }

        List<ProductListDTO> productDTOList = productService.getProductListWithKeyword(keyword, filters, minPrice, maxPrice);

        model.addAttribute("productDTOList", productDTOList);
        model.addAttribute("filters", filters);
        model.addAttribute("keyword", keyword);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        return "/product/search";
    }

    @GetMapping("/product/view")
    public String view(int productId, Model model, HttpSession session) throws JsonProcessingException {

        Product_V_DTO productDTO = productService.getProduct_V_ById(productId);

        List<ProductVariantsWithoutProductDTO> productVariants = productDTO.getProductVariants();

        LinkedHashMap<String, List<String>> options = (LinkedHashMap<String, List<String>>) productDTO.getOptions();

        String productDTOJson = objectMapper.writeValueAsString(productDTO);
        model.addAttribute("options", options);
        model.addAttribute("productDTOJson", productDTOJson);
        model.addAttribute("productDTO", productDTO);

        session.setAttribute("productDTO", productDTO);
        log.info("productDTO : " + productDTO);
        log.info("productDTOJson : " + productDTOJson);
        log.info("options : " + options);

        model.addAttribute("productVariants", productVariants);

        return "/product/view";
    }

}
