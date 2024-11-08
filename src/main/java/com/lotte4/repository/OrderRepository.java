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
       - 2024-11-08 전규찬 모든 주문을 최신순으로 조회하는 기능 추가 / 기간별 주문 목록 조회 기능 추가

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

    // 해당 사용자의 모든 주문을 최신순으로 조회
    List<Order> findAllByMemberInfoOrderByBuyDateDesc(MemberInfo memberInfo);

    // 1. 현재 시점 기준 상대적인 기간 조회
    List<Order> findAllByMemberInfoAndBuyDateAfterOrderByBuyDateDesc(MemberInfo memberInfo, LocalDateTime buyDate);

    // 2. 특정 월 단위로 조회
    @Query("SELECT o FROM Order o WHERE FUNCTION('MONTH', o.buyDate) = :month AND FUNCTION('YEAR', o.buyDate) = :year ORDER BY o.buyDate DESC")
    List<Order> findAllByMonthAndYear(@Param("month") int month, @Param("year") int year);

    // 3. 사용자 지정 기간 조회
    List<Order> findAllByBuyDateBetweenOrderByBuyDateDesc(LocalDateTime startDate, LocalDateTime endDate);
}
