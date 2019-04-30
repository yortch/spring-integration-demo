package com.demo.integration.endpoint;

import com.demo.integration.converter.OrderToWebServiceType;
import com.demo.integration.dto.Order;
import com.demo.integration.service.OrderService;
import com.integration.demo.webservice.client.OrderRequest;
import com.integration.demo.webservice.client.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
public class OrderEndpoint {
    private final OrderService orderService;

    @Autowired
    private OrderToWebServiceType orderToWebServiceTypeConverter;

    public OrderEndpoint(OrderService orderService) {
        this.orderService = orderService;
    }

    @PayloadRoot(localPart = "orderRequest", namespace = "http://demo/schemas")
    public @ResponsePayload
    OrderResponse getOrder(@RequestPayload OrderRequest orderRequest) {
        OrderResponse response = new OrderResponse();
        Order order = orderService.getOrder(orderRequest.getOrder().getId());
        response.setOrder(orderToWebServiceTypeConverter.convert(order));
        return response;
    }


}