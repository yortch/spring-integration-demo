package com.ext.demo.integration.orderdetail;

import com.ext.demo.integration.channel.ChannelNames;
import com.integration.ext.demo.webservice.client.OrderDetail;
import com.integration.ext.demo.webservice.client.OrderDetailResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailResponseHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @ServiceActivator(inputChannel = ChannelNames.ORDER_DETAIL_RESPONSE, outputChannel = ChannelNames.ORDER_RESPONSE_BUILDER)
    public OrderDetail getResponse(Message<OrderDetailResponse> msg) {
        OrderDetailResponse response = msg.getPayload();
        logger.info("Response with order ID [{}] and total: {}", response.getOrderDetail().getId(), response.getOrderDetail().getTotal());

        return response.getOrderDetail();
    }
}
