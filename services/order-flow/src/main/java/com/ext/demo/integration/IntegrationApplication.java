package com.ext.demo.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.config.EnableIntegrationManagement;
import org.springframework.integration.graph.IntegrationGraphServer;
import org.springframework.integration.http.config.EnableIntegrationGraphController;


@SpringBootApplication
@EnableIntegration
//@EnableIntegrationManagement(statsEnabled = "_org.springframework.integration.errorLogger.handler",
//		countsEnabled = "!*",
//		defaultLoggingEnabled = "true")
@EnableIntegrationGraphController(path = "/integration", allowedOrigins="http://localhost:8082")
@IntegrationComponentScan
public class IntegrationApplication {

	private static Logger LOG = LoggerFactory.getLogger(IntegrationApplication.class);

	@Bean
	public IntegrationGraphServer integrationGraphServer() {
		return new IntegrationGraphServer();
	}

	public static void main(String[] args) {
		SpringApplication.run(IntegrationApplication.class, args);
	}



}
