package com.ext.demo.integration.transformer;

import com.integration.ext.demo.webservice.client.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToExtOrderType implements Converter<com.integration.demo.webservice.client.Order, Order> {

    @Override
    public Order convert(com.integration.demo.webservice.client.Order source) {
        Order order = new Order();
        order.setId(source.getId());
        return order;
    }
}
