package com.lotte4.repository;

import com.lotte4.dto.DeliveryDTO;
import com.lotte4.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
     날짜 : 2024/10/30
     이름 : 조수빈
     내용 : DeliveryRepository 생성

     수정이력
       - 2024-10-30 조수빈 : 조회조건을 기존 Jpa받는것 보다 fetch로 받아서 직접적으로 받는게 좋다고 나와서 적용함

*/


//조회 조건은 fetch로 받는게 좋다고 나와서 사용해봄
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    @Query("SELECT d FROM Delivery d JOIN FETCH d.orderItem")
    List<Delivery> findAllWithOrders();

    @Query("SELECT d FROM Delivery d WHERE d.deliveryWaybill = :deliveryWaybill")
    DeliveryDTO findByDeliveryWaybillEqual(@Param("deliveryWaybill") String deliveryWaybill);


}