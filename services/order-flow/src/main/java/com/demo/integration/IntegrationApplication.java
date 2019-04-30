package com.demo.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ws.SimpleWebServiceInboundGateway;
import org.springframework.integration.ws.SimpleWebServiceOutboundGateway;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.ws.client.support.destination.DestinationProvider;
import org.springframework.ws.config.annotation.EnableWs;

@SpringBootApplication
public class IntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegrationApplication.class, args);
	}

	@Bean
	SimpleWebServiceInboundGateway inboundGateway() {
		SimpleWebServiceInboundGateway inboundGateway = new SimpleWebServiceInboundGateway();
		//inboundGateway.setRequestChannel();
		return inboundGateway;

	}

}
