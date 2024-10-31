package com.lotte4.repository;


import com.lotte4.entity.Cart;
import com.lotte4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/*
     날짜 : 2024/10/30
     이름 : 강은경
     내용 : CartRepository 생성

     수정이력
      - 2024/10/30 강은경 - uid와 variant_id에 해당하는 cart 조회하는 쿼리 추가
*/
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    // Cart findByUserId
    // Cart entity 내의 User 객체의 userId를 기준으로 데이터를 검색
    Optional<List<Cart>> findByUser_Uid(String uid);

    // uid와 variant_id에 해당하는 cart 조회
    @Query("SELECT c FROM Cart c WHERE c.user.uid = :userId AND c.productVariants.variant_id = :variant_id")
    Optional<Cart> findByUserUidAndProductVariantId(@Param("userId") String userId, @Param("variant_id") int variantId);


}
