package com.lotte4.controller.pagecontroller.admin.order;

import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.lotte4.controller.pagecontroller.CSB.OrderController;
import com.lotte4.dto.DeliveryDTO;
import com.lotte4.dto.OrderDTO;
import com.lotte4.dto.OrderItemsDTO;
import com.lotte4.dto.UserDTO;
import com.lotte4.entity.Order;
import com.lotte4.entity.OrderItems;
import com.lotte4.repository.OrderItemsRepository;
import com.lotte4.repository.OrderRepository;
import com.lotte4.service.DeliveryService;
import com.lotte4.service.OrderService;
import com.lotte4.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Log4j2
@AllArgsConstructor
@Controller
public class AdminOrderController {

    private final OrderService orderService;
    private final ParameterNamesModule parameterNamesModule;

    @GetMapping("/admin/order/list")
    public String orderList(Model model) {
        List<Order> orders = orderService.getAllOrders();

        Map<Integer, Integer> orderItemCounts = orderService.getOrderItemCounts(orders);
        model.addAttribute("orderItemCounts", orderItemCounts);


        List<OrderItems> orderItems = orderService.getAllOrderItems();
        model.addAttribute("orderItemDTO", orderItems);

        List<UserDTO> users = orderService.selectAllUser();

        model.addAttribute("users", users);
        model.addAttribute("orderDTO" , orderService.getAllOrders());
        log.info("TetastajksdeaT = " + orderService.getAllOrderItems());
        log.info("TetastajksdeaT = " + orderService.getAllOrders());
        log.info("111111",users);

        return "/admin/order/list";
    }

    @GetMapping("admin/order/list/{type}")
    public String orderSelect(Model model,@PathVariable String type) {

        return "/admin/order/list";
    }


    @GetMapping("/admin/order/delivery")
    public String orderDelivery(Model model) {
        return "/admin/order/delivery";
    }





}
