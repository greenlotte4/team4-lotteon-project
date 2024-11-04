package com.lotte4.service;


import com.lotte4.dto.CartDTO;
import com.lotte4.dto.CartResponseDTO;
import com.lotte4.dto.ProductCateDTO;
import com.lotte4.dto.UserDTO;
import com.lotte4.dto.admin.config.InfoDTO;
import com.lotte4.entity.Cart;
import com.lotte4.entity.ProductCate;
import com.lotte4.entity.User;
import com.lotte4.repository.CartRepository;
import com.lotte4.repository.ProductVariantsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/*
     날짜 : 2024/10/30
     이름 : 강은경
     내용 : CartService 생성

     수정이력
      - 2024/10/30 강은경 - cart insert 하는 메서드 추가
      - 2024/11/02 조수빈 - cart select 하는 메서드 추가
*/
@Log4j2
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductVariantsRepository productVariantsRepository;
    private final ModelMapper modelMapper;


    // 사용자 아이디와 장바구니 ID 목록으로 CartDTO 목록 조회
    public List<CartDTO> getCartItemsByIds(String uid, List<Integer> cartIds) {
        // 사용자의 cartIds에 포함된 장바구니 항목 조회
        return cartRepository.findByUserUidAndCartIdIn(uid, cartIds).stream()
                .map(cart -> modelMapper.map(cart, CartDTO.class))
                .collect(Collectors.toList());
    }

    // 장바구니 목록 select
    public List<CartDTO> getCartByUserId(String uid) {
        List<CartDTO> cartDTOList = new ArrayList<>();

        cartRepository.findByUser_Uid(uid)
                .orElse(Collections.emptyList())
                .forEach(cart -> cartDTOList.add(modelMapper.map(cart, CartDTO.class)));

        return cartDTOList;
    }

    // 장바구니 삭제
    public void deleteCartItems(int cartId){
        cartRepository.deleteById(cartId); // cartId로 cart 삭제
    }

    // cart insert
    public Cart insertCart(CartResponseDTO cartResponseDTO) {

        Cart cart = modelMapper.map(cartResponseDTO, Cart.class);
        log.info("cart : " + cart);

        // 기존 장바구니에 동일한 상품이 있는지 확인
        Optional<Cart> existingCartOptional = cartRepository.findByUserUidAndProductVariantId(
                cartResponseDTO.getUser().getUid(),
                cartResponseDTO.getProductVariants().getVariant_id()
        );

        log.info("existingCartDTO : " + existingCartOptional);

        if (existingCartOptional.isPresent()) {
            // 기존 상품이 있으면 count만 업데이트
            Cart existingCart = existingCartOptional.get();
            existingCart.setCount(existingCart.getCount() + cart.getCount());

            log.info("existingCart : " + existingCart);
            return cartRepository.save(existingCart);  // 기존 엔티티를 업데이트
        } else {
            // 기존 장바구니에 동일한 상품이 없으면 새로 저장
            return cartRepository.save(cart);
        }
    }





}
