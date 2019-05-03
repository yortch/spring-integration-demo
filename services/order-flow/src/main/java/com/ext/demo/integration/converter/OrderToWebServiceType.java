package com.ext.demo.integration.converter;

import com.ext.demo.integration.dto.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToWebServiceType implements Converter<Order, com.integration.demo.webservice.client.Order> {

    @Override
    public com.integration.demo.webservice.client.Order convert(Order source) {
        com.integration.demo.webservice.client.Order order = new com.integration.demo.webservice.client.Order();
        order.setId(source.getId());
        return order;
    }
}
