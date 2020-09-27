package com.derick.controllers;

import com.derick.entities.Order;
import com.derick.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders")
public class OrderController extends AbstractController<Order>{

    @Autowired
    public OrderController(OrderService service) {
        super(service);
    }
}
