package com.ext.demo.integration.transformer;

import com.ext.demo.integration.channel.ChannelNames;
import com.integration.demo.webservice.client.Order;
import com.integration.demo.webservice.client.OrderResponse;
import com.integration.ext.demo.webservice.client.OrderDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class OrderTransformer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transformer(inputChannel=ChannelNames.ORDER_RESPONSE_BUILDER, outputChannel = ChannelNames.ORDER_RESPONSE)
    public OrderResponse buildResponse(Message<OrderDetail> msg) {
        logger.info("Building request for order [{}]", msg.getPayload().getId());
        OrderDetail detail = msg.getPayload();
        OrderResponse response = new OrderResponse();
        Order order = new Order();
        order.setId(detail.getId());
        response.setOrder(order);
        return response;
    }

}
