package com.lotte4.controller.pagecontroller.admin.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminOrderController {


    @GetMapping("/admin/order/list")
    public String orderList() {
        return "/admin/order/list";
    }


    @GetMapping("/admin/order/delivery")
    public String orderDelivery() {
        return "/admin/order/delivery";
    }
}
