package com.lotte4.controller.pagecontroller.admin.order;


import com.lotte4.dto.DeliveryDTO;
import com.lotte4.service.DeliveryService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@Log4j2
@AllArgsConstructor
@RestController
public class AdminOrderRestController {

    private final DeliveryService deliveryService;

    @PostMapping("/admin/order/delivery/save")
    public ResponseEntity<DeliveryDTO> deliveryInsert(
            @RequestParam Long orderId,
            @RequestBody DeliveryDTO deliveryDTO) {
//        DeliveryDTO savedDelivery = deliveryService.saveDelivery(orderId, deliveryDTO);
//        return ResponseEntity.ok(savedDelivery);
        return null;
    }

}
