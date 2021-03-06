package com.derick.controllers;

import com.derick.entities.Client;
import com.derick.entities.Order;
import com.derick.entities.dto.ClientNewDTO;
import com.derick.entities.dto.OrderDTO;
import com.derick.services.AbstractService;
import com.derick.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/orders")
public class OrderController extends AbstractController<Order, OrderDTO>{

    @Autowired
    private OrderService orderService;

    @Override
    public AbstractService<Order, OrderDTO> getService() {
        return orderService;
    }

    @PostMapping("/newOrder")
    public ResponseEntity<Void> insertNewOrder(@Valid @RequestBody Order order) {
        order = orderService.insertNewOrder(order);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/pageByClient")
    public ResponseEntity<Page<Order>> findPageByClient(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "orderBy", defaultValue = "date") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction
    ){
        Page<Order> orderPage = orderService.findPageByClient(page, size, orderBy, direction);
        return ResponseEntity.ok().body(orderPage);

    }
}
