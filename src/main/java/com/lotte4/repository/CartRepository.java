package com.lotte4.repository;


import com.lotte4.entity.Cart;
import com.lotte4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    // Cart findByUserId
    // Cart entity 내의 User 객체의 userId를 기준으로 데이터를 검색
    Optional<List<Cart>> findByUser_Uid(String uid);



}
