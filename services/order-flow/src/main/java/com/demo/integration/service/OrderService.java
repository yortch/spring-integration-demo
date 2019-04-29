package com.demo.integration.service;

import com.demo.integration.dto.Order;

public interface OrderService {

    public Order getOrder(String id);

    public void createOrder(Order order);
}
