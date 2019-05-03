package com.ext.demo.integration.endpoint;

import com.ext.demo.integration.converter.OrderToWebServiceType;
import com.ext.demo.integration.dto.Order;
import com.ext.demo.integration.service.OrderService;
import com.integration.demo.webservice.client.OrderRequest;
import com.integration.demo.webservice.client.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;


@Configuration
public class OrderEndpoint {
    private final OrderService orderService;

    @Autowired
    private OrderToWebServiceType orderToWebServiceTypeConverter;

    public OrderEndpoint(OrderService orderService) {
        this.orderService = orderService;
    }

    @ServiceActivator(inputChannel="orderInputChannel")
    OrderResponse getOrder(OrderRequest orderRequest) {
        OrderResponse response = new OrderResponse();
        Order order = orderService.getOrder(orderRequest.getOrder().getId());
        response.setOrder(orderToWebServiceTypeConverter.convert(order));
        return response;
    }


}