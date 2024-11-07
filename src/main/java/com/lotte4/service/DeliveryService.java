package com.lotte4.service;

import com.lotte4.dto.DeliveryDTO;
import com.lotte4.dto.OrderDTO;
import com.lotte4.entity.Delivery;
import com.lotte4.entity.Order;
import com.lotte4.entity.OrderItems;
import com.lotte4.repository.DeliveryRepository;
import com.lotte4.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


/*
     날짜 : 2024/10/30
     이름 : 조수빈
     내용 : DeliveryService 생성
*/


@Log4j2
@Service
@Transactional
@AllArgsConstructor
public class DeliveryService {

    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final OrderService orderService;
    private final ModelMapper getModelMapper;


//    //배송 객체에 order를 포함시켜 반환하는 방식
//    public List<DeliveryDTO> selectDeliveries(){
//        List<Delivery> deliveryEntities = deliveryRepository.findAllWithOrders();
//        List<DeliveryDTO> deliveries = deliveryEntities.stream()
//                .map(delivery -> {
//                    DeliveryDTO deliveryDTO = new DeliveryDTO(
//                            delivery.getDeliveryNo(),
//                            delivery.getDeliveryDate(),
//                            delivery.getDeliveryTime(),
//                            delivery.getDeliveryCompany(),
//                            delivery.getDeliveryWaybill(),
//                            delivery.getWaybillDate(),
//                            null,
//                            delivery.getContent()
//                    );
//
//                    if (delivery.getOrder() != null) {
//                        Order order = delivery.getOrder();
//                        OrderDTO orderDTO = new OrderDTO(
//                                order.getOrderId(),
//                                order.getUsePoint(),
//                                order.getTotalPrice(),
//                                order.getRecipName(),
//                                order.getRecipHp(),
//                                order.getRecipZip(),
//                                order.getRecipAddr1(),
//                                order.getRecipAddr2(),
//                                order.getPayment(),
//                                order.getStatus(),
//                                order.getBuyDate(),
//                                order.getCouponUse(),
//                                order.getProductVariants(),
//                                order.getMemberInfo().toDTO(),
//                                null
//                        );
//                        deliveryDTO.setOrderDTO(orderDTO);
//                    }
//                    return deliveryDTO;
//                })
//                .collect(Collectors.toList());
//
//        return deliveries;
//
//    }


    public DeliveryDTO saveDelivery(Long orderId, DeliveryDTO deliveryDTO) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId.intValue());
        if (optionalOrder.isEmpty()) {
            log.warn("주문을 찾을 수 없습니다. Order ID: " + orderId);
            return null;
        }

        Order order = optionalOrder.get();
        OrderItems orderItems = new OrderItems();

        // 만약 배송이 없으면 새로운 배송 생성 아니면 업데이트(기존의 데이터에 업데이트 할 수 있게 준비)
        Delivery delivery = new Delivery();


        delivery.setDeliveryCompany(deliveryDTO.getDeliveryCompany());
        delivery.setDeliveryWaybill(deliveryDTO.getDeliveryWaybill());
        delivery.setContent(deliveryDTO.getContent());
        delivery.setDeliveryDate(deliveryDTO.getDeliveryDate() != null ? deliveryDTO.getDeliveryDate() : LocalDateTime.now());
        delivery.setDeliveryTime(deliveryDTO.getDeliveryTime() != null ? deliveryDTO.getDeliveryTime() : delivery.getDeliveryDate().plusDays(3));

        delivery = deliveryRepository.save(delivery);

        DeliveryDTO savedDeliveryDTO = new DeliveryDTO();
        savedDeliveryDTO.setDeliveryId(delivery.getDeliveryId());
        savedDeliveryDTO.setDeliveryCompany(delivery.getDeliveryCompany());
        savedDeliveryDTO.setDeliveryWaybill(delivery.getDeliveryWaybill());
        savedDeliveryDTO.setContent(delivery.getContent());
        savedDeliveryDTO.setDeliveryDate(delivery.getDeliveryDate());
        savedDeliveryDTO.setDeliveryTime(delivery.getDeliveryTime());

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setRecipName(order.getRecipName());
        orderDTO.setRecipAddr1(order.getRecipAddr1());
        savedDeliveryDTO.setOrderDTO(orderDTO);

        log.info("Saved delivery: " + savedDeliveryDTO);

        return savedDeliveryDTO;
    }

    public String getDeliveryContentByOrderItem(OrderItems orderItems) {
        Delivery delivery = deliveryRepository.findByOrderItem(orderItems);
        return delivery.getContent();
    }

}
