package com.lotte4.repository;

import com.lotte4.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
    @Query("select oi from OrderItems oi where oi.order.orderId = :orderId")
    List<OrderItems> findByOrderId(@Param("orderId") Integer orderId);
}
