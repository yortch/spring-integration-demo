package com.ext.demo.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;


@SpringBootApplication
@EnableIntegration
@IntegrationComponentScan
public class OrderDetailIntegrationApplication {

	private static Logger LOG = LoggerFactory.getLogger(OrderDetailIntegrationApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(OrderDetailIntegrationApplication.class, args);
	}



}
