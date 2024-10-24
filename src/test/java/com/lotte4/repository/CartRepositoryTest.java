package com.lotte4.repository;

import com.lotte4.entity.Cart;
import com.lotte4.entity.ProductVariants;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartRepositoryTest {


    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductVariantsRepository productVariantsRepository;


    @Test
    @Transactional
    void selectCart(){
        System.out.println(cartRepository.findById(1).get());
    }

    @Test
    @Transactional
    void selectCartById(){

        Optional<List<Cart>> cartOptional = cartRepository.findByUser_UserId(3);

        if(cartOptional.isPresent()){
            List<Cart> cartList = cartOptional.get();
            System.out.println(cartList);
        }

        System.out.println("없다");
    }

    @Test
    @Transactional
    @Rollback(false) // 롤백 방지
    void inserCart() {

       ProductVariants prod= ProductVariants.builder().variant_id(1).build();

       Cart cart= Cart.builder()
                    .user(userRepository.findById(4).get())
                    .count(2)
                    .productVariants(prod)
                    .rDate("asdfasdf")
                    .build();

        Cart savedCart = cartRepository.save(cart);
        System.out.println(savedCart);
    }



}