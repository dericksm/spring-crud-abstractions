package com.derick.services;

import com.derick.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends AbstractService<Order> {
    public OrderService(JpaRepository<Order, Integer> repository) {
        super(repository);
    }
}
