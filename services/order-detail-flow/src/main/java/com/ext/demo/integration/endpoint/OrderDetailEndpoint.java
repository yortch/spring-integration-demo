package com.ext.demo.integration.endpoint;


import com.integration.ext.demo.webservice.client.OrderDetail;
import com.integration.ext.demo.webservice.client.OrderDetailRequest;
import com.integration.ext.demo.webservice.client.OrderDetailResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;

import java.util.Random;


@Configuration
public class OrderDetailEndpoint {

    @ServiceActivator(inputChannel="orderDetailInputChannel")
    OrderDetailResponse getOrderDetail(OrderDetailRequest orderDetailRequest) {
        OrderDetailResponse response = new OrderDetailResponse();
        OrderDetail detail = new OrderDetail();
        detail.setId(orderDetailRequest.getOrder().getId());
        Random randomizer = new Random();
        detail.setTotal(String.valueOf(randomizer.nextInt()));
        response.setOrderDetail(detail);
        return response;
    }


}