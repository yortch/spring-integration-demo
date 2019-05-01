package com.demo.integration.endpoint;

import com.demo.integration.converter.OrderToWebServiceType;
import com.demo.integration.dto.Order;
import com.demo.integration.service.OrderService;
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