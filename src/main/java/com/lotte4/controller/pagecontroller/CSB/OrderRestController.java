package com.lotte4.controller.pagecontroller.CSB;

import com.lotte4.dto.OrderDTO;
import com.lotte4.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

@Log4j2
@AllArgsConstructor
@RestController
public class OrderRestController {

    private final OrderService orderService;

    @PostMapping("/product/order/save")
    public ResponseEntity<Map<String, Object>> insertOrder(@RequestBody OrderDTO orderDTO) {
        orderService.insertOrder(orderDTO);
        log.info("Insert Order: " + orderDTO);

        Map<String, Object> response = Map.of("success", true, "message", "Order saved successfully");
        return ResponseEntity.ok(response);
    }
}
