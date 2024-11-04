package com.lotte4.controller.pagecontroller.admin.order;

import com.lotte4.controller.pagecontroller.CSB.OrderController;
import com.lotte4.dto.DeliveryDTO;
import com.lotte4.dto.OrderDTO;
import com.lotte4.dto.UserDTO;
import com.lotte4.service.DeliveryService;
import com.lotte4.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;


@Log4j2
@AllArgsConstructor
@Controller
public class AdminOrderController {

    private final OrderService orderService;
    private final DeliveryService deliveryService;


    @GetMapping("/admin/order/list")
    public String orderList(Model model) {
        List<OrderDTO> orderDTOS = orderService.selectAllOrders();
        model.addAttribute("orderDTOS", orderDTOS);
        log.info("orderDTOS = "+ orderDTOS);

        List<UserDTO> userDTOS = orderService.selectAllUser();
        model.addAttribute("userDTOS", userDTOS);
        log.info("userDTOS = "+ userDTOS);

        return "/admin/order/list";
    }

    @GetMapping("admin/order/list/{type}")
    public String orderSelect(Model model,@PathVariable String type) {
        List<OrderDTO> orderDTOS = orderService.selectAllOrders();
        model.addAttribute("orderDTOS", orderDTOS);
        log.info("orderDTOS = " + orderDTOS);
        return "/admin/order/list";
    }


    @GetMapping("/admin/order/delivery")
    public String orderDelivery(Model model) {

//        model.addAttribute("deliverys", deliveryService.selectDeliveries());
//        log.info("deliverys = "+ deliveryService.selectDeliveries());

        return "/admin/order/delivery";
    }





}
