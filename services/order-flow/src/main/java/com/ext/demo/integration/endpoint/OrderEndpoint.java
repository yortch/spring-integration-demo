package com.ext.demo.integration.endpoint;

import com.ext.demo.integration.channel.ChannelNames;
import com.ext.demo.integration.service.OrderService;
import com.integration.demo.webservice.client.OrderRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.handler.BridgeHandler;

@Configuration
public class OrderEndpoint {
    private final OrderService orderService;

    public OrderEndpoint(OrderService orderService) {
        this.orderService = orderService;
    }

    @ServiceActivator(inputChannel=ChannelNames.ORDER_INVOCATION, outputChannel = ChannelNames.ORDER_DETAIL_REQUEST_BUILDER)
    OrderRequest getOrder(OrderRequest orderRequest) {
        return orderRequest;
    }


}