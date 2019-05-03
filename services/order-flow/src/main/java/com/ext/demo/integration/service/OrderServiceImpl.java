package com.ext.demo.integration.service;

import com.ext.demo.integration.dto.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    @Override
    public Order getOrder(String id) {
        Order order = new Order();
        order.setId(id);
        return order;
    }

    @Override
    public void createOrder(Order order) {

    }
}
