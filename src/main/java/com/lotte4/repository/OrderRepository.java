package com.lotte4.repository;

import com.lotte4.entity.MemberInfo;
import com.lotte4.entity.Order;
import com.lotte4.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

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
    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems oi JOIN FETCH oi.delivery ORDER BY o.orderId DESC")
    List<Order> findOrdersWithDetailsAndDelivery(Pageable pageable);

    @Query("SELECT oi FROM OrderItems oi JOIN FETCH oi.delivery ORDER BY oi.orderItemId DESC")
    List<OrderItems> findAllOrderItems();

    Order findFirstByMemberInfoOrderByBuyDateDesc(MemberInfo memberInfo);

    @Query("select o from Order o where o.Status = :status")
    List<Order> findByStatus(@Param("status") int status);

    @Procedure(name = "testProcedure")
    void testProcedure();
}
