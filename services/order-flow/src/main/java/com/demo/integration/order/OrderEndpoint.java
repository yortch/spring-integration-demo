package com.demo.integration.order;

import com.demo.integration.channel.ChannelNames;
import com.integration.demo.webservice.client.Order;
import com.integration.demo.webservice.client.OrderRequest;
import com.integration.demo.webservice.client.OrderResponse;
import com.integration.ext.demo.webservice.client.OrderDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;

@Configuration
public class OrderEndpoint {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ServiceActivator(inputChannel = ChannelNames.ORDER_INVOCATION, outputChannel = ChannelNames.ORDER_DETAIL_REQUEST_BUILDER)
    OrderRequest getOrder(OrderRequest orderRequest) {
        return orderRequest;
    }

    @Transformer(inputChannel = ChannelNames.ORDER_RESPONSE_BUILDER, outputChannel = ChannelNames.ORDER_RESPONSE)
    public OrderResponse getOrderResponse(Message<OrderDetail> msg) {
        logger.info("Building request for order [{}]", msg.getPayload().getId());
        OrderDetail detail = msg.getPayload();
        OrderResponse response = new OrderResponse();
        Order order = new Order();
        order.setId(detail.getId());
        response.setOrder(order);
        return response;
    }

}