package com.lotte4.repository;

import com.lotte4.entity.MemberInfo;
import com.lotte4.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/*
     날짜 : 2024/10/30
     이름 : 조수빈
     내용 : OrderRepository 생성

     수정이력
       -

*/

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findFirstByMemberInfoOrderByBuyDateDesc(MemberInfo memberInfo);
}
