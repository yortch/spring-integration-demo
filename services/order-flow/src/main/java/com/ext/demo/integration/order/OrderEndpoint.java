package com.ext.demo.integration.order;

import com.ext.demo.integration.channel.ChannelNames;
import com.integration.demo.webservice.client.OrderRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;

@Configuration
public class OrderEndpoint {

    @ServiceActivator(inputChannel=ChannelNames.ORDER_INVOCATION, outputChannel = ChannelNames.ORDER_DETAIL_REQUEST_BUILDER)
    OrderRequest getOrder(OrderRequest orderRequest) {
        return orderRequest;
    }


}