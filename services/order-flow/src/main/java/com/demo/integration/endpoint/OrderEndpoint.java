package com.demo.integration.endpoint;

import com.demo.integration.dto.Order;
import com.demo.integration.service.OrderService;
import com.integration.demo.webservice.client.ObjectFactory;
import com.integration.demo.webservice.client.OrderRequest;
import com.integration.demo.webservice.client.OrderResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBElement;

@Endpoint
public class OrderEndpoint {
    private final OrderService orderService;

    private ObjectFactory objectFactory = new ObjectFactory();

    public OrderEndpoint(OrderService orderService) {
        this.orderService = orderService;
    }

    @PayloadRoot(localPart = "orderRequest", namespace = "http://demo/schemas")
    public @ResponsePayload
    JAXBElement<OrderResponse> getOrder(@RequestPayload JAXBElement<OrderRequest> orderRequest) {
        OrderResponse response = new OrderResponse();
        Order order = orderService.getOrder(orderRequest.getValue().getOrder().getId());
        response.setOrder(order.toWebServiceType());
        return objectFactory.createOrderResponse(response);
    }


}