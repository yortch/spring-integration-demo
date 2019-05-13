package com.ext.demo.integration.config;

import com.ext.demo.integration.channel.ChannelNames;
import com.integration.ext.demo.webservice.client.OrderDetailRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.ws.MarshallingWebServiceOutboundGateway;
import org.springframework.messaging.MessageHandler;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.ClassUtils;

@Configuration
public class OrderDetailServiceConfiguration {

    @Value("${order.detail.service.url}")
    private String orderDetailServicerUrl;

    @Bean
    @ServiceActivator(inputChannel = ChannelNames.ORDER_DETAIL_INVOCATION)
    public MessageHandler orderDetailOutboundGateway() {
        MarshallingWebServiceOutboundGateway gateway = new MarshallingWebServiceOutboundGateway(orderDetailServicerUrl, orderDetailServiceMarshaller());
        gateway.setOutputChannelName(ChannelNames.ORDER_DETAIL_RESPONSE);

        return gateway;
    }

    @Bean
    Jaxb2Marshaller orderDetailServiceMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(ClassUtils.getPackageName(OrderDetailRequest.class));
        return marshaller;
    }
}
