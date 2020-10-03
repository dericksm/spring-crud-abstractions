package com.derick.services;

import com.derick.entities.Order;
import com.derick.entities.OrderItem;
import com.derick.entities.PaymentSlip;
import com.derick.entities.Product;
import com.derick.entities.dto.OrderDTO;
import com.derick.entities.enums.PaymentStatus;
import com.derick.repositories.OrderItemRepository;
import com.derick.repositories.OrderRepository;
import com.derick.repositories.PaymentRepository;
import com.derick.repositories.ProductRepository;
import com.derick.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService extends AbstractService<Order, OrderDTO> {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public JpaRepository<Order, Integer> getRepository() {
        return orderRepository;
    }

    @Transactional
    public Order insertNewOrder(Order order) {
        order.setId(null);
        order.setDate(new Date());
        order.getPayment().setPaymentStatus(PaymentStatus.WAITING);
        order.getPayment().setOrder(order);
        if(order.getPayment() instanceof PaymentSlip){
            PaymentSlip payment = (PaymentSlip) order.getPayment();
            DateUtils.addWeek(payment, order.getDate());
        }
        order = orderRepository.save(order);
        paymentRepository.save(order.getPayment());
        for (OrderItem item : order.getItems()) {
            item.setDiscount(0D);
            item.setPrice(productRepository.findById(item.getProduct().getId()).get().getPrice());
            item.setOrder(order);
        }
        orderItemRepository.saveAll(order.getItems());
        return order;
    }
}
