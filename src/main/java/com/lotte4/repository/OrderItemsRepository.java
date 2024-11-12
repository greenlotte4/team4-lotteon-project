package com.lotte4.repository;

import com.lotte4.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
    @Query("select oi from OrderItems oi where oi.order.orderId = :orderId")
    List<OrderItems> findByOrderId(@Param("orderId") Integer orderId);

    @Query("SELECT COUNT(oi) FROM OrderItems oi WHERE DATE(oi.order.buyDate) = :today AND oi.status = :status")
    int findAllByDayWithStatus(@Param("today") LocalDate day, @Param("status") int status);

    @Query("SELECT COUNT(oi) FROM OrderItems oi WHERE DATE(oi.order.buyDate) BETWEEN :startDate AND :today AND oi.status = :status")
    int findAllByLastFiveDaysWithStatus(@Param("startDate") LocalDate startDate, @Param("today") LocalDate today, @Param("status") int status);
}
