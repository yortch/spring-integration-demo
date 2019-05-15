package com.demo.integration.transformer;

import com.demo.integration.channel.ChannelNames;
import com.integration.demo.webservice.client.OrderRequest;
import com.integration.ext.demo.webservice.client.Order;
import com.integration.ext.demo.webservice.client.OrderDetailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailTransformer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private OrderToExtOrderType orderToExtOrderTypeConverter;

    @Transformer(inputChannel=ChannelNames.ORDER_DETAIL_REQUEST_BUILDER, outputChannel=ChannelNames.ORDER_DETAIL_INVOCATION)
    public OrderDetailRequest buildRequest(Message<OrderRequest> msg) {
        logger.info("Building request for order Id [{}]", msg.getPayload().getOrder().getId());
        OrderRequest orderRequest = msg.getPayload();
        Order order = orderToExtOrderTypeConverter.convert(orderRequest.getOrder());
        OrderDetailRequest request = new OrderDetailRequest();
        request.setOrder(order);
        return request;
    }


}
