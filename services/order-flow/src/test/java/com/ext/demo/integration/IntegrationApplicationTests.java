package com.ext.demo.integration;

import com.integration.demo.webservice.client.Order;
import com.integration.demo.webservice.client.OrderRequest;
import com.integration.demo.webservice.client.OrderResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ClassUtils;
import org.springframework.ws.client.core.WebServiceTemplate;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationApplicationTests {

	private Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

	@LocalServerPort
	private int port = 0;

	private String uri;

	@Before
	public void init() throws Exception {
		marshaller.setPackagesToScan(ClassUtils.getPackageName(OrderRequest.class));
		marshaller.afterPropertiesSet();
		uri = "http://localhost:" + port + "/orderService";
	}
	@Test
	public void contextLoads() {
	}

	@Test
	public void orderSendAndReceive() {
		WebServiceTemplate template = new WebServiceTemplate(marshaller);
		OrderRequest request = new OrderRequest();
		Order order = new Order();
		String orderId = "123";
		order.setId(orderId);
		request.setOrder(order);
		Object respObject = template.marshalSendAndReceive(uri, request);
		assertThat(respObject).isNotNull();
		assertThat(respObject instanceof OrderResponse).isTrue();
		OrderResponse resp = (OrderResponse)respObject;
		Order orderResp = resp.getOrder();
		assertThat(orderResp).isNotNull();
		assertThat(orderResp.getId().equals(orderId));
	}


}
