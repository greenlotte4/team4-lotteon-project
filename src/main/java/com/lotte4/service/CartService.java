package com.lotte4.service;


import com.lotte4.dto.CartDTO;
import com.lotte4.dto.UserDTO;
import com.lotte4.entity.Cart;
import com.lotte4.entity.User;
import com.lotte4.repository.CartRepository;
import com.lotte4.repository.ProductVariantsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductVariantsRepository productVariantsRepository;
    private final ModelMapper modelMapper;

//    public List<Cart> selectCartsByUserId(int userId){
//        return cartRepository.findByUserId(userId);
//    }

    // 장바구니 목록 select
    public List<CartDTO> getCartByUserId(int user_id) {
        return cartRepository.findByUser_UserId(user_id)
                .map(cartList -> cartList.stream()
                        .map(cart -> modelMapper.map(cart, CartDTO.class))  // 각 Cart 객체를 CartDTO로 변환
                        .collect(Collectors.toList())  // List<CartDTO>로 수집
                )
                .orElse(Collections.emptyList());  // 데이터가 없을 경우 빈 리스트 반환
    }

    // 장바구니 삭제
    public void deleteCartItems(int cartId){
        cartRepository.deleteById(cartId); // cartId로 cart 삭제
    }





}
