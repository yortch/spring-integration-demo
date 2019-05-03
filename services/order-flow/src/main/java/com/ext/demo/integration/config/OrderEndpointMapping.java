package com.ext.demo.integration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.ws.MarshallingWebServiceInboundGateway;
import org.springframework.ws.server.endpoint.mapping.UriEndpointMapping;

@Configuration
public class OrderEndpointMapping {

    @Autowired
    private MarshallingWebServiceInboundGateway orderInboundGateway;

    @Bean
    public UriEndpointMapping uriEndpointMapping() {
        UriEndpointMapping uriEndpointMapping = new UriEndpointMapping();
        uriEndpointMapping.setDefaultEndpoint(orderInboundGateway);
        return uriEndpointMapping;
    }
}
