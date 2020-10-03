package com.derick.services;

import com.derick.entities.Order;
import com.derick.entities.OrderItem;
import com.derick.entities.PaymentSlip;
import com.derick.entities.dto.OrderDTO;
import com.derick.entities.enums.PaymentStatus;
import com.derick.repositories.*;
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

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmailService emailService;

    @Override
    public JpaRepository<Order, Integer> getRepository() {
        return orderRepository;
    }

    @Transactional
    public Order insertNewOrder(Order order) {
        order.setId(null);
        order.setDate(new Date());
        order.setClient(clientService.findById(order.getClient().getId()));
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
            item.setProduct(productRepository.findById(item.getProduct().getId()).get());
            item.setPrice(item.getProduct().getPrice());
            item.setOrder(order);
        }
        orderItemRepository.saveAll(order.getItems());
        emailService.sendOrderConfirmationHtmlEmail(order);
        return order;
    }
}
