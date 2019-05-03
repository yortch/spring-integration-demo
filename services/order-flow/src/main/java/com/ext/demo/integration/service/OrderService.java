package com.ext.demo.integration.service;

import com.ext.demo.integration.dto.Order;

public interface OrderService {

    public Order getOrder(String id);

    public void createOrder(Order order);
}
