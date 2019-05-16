package com.demo.integration;

import com.demo.integration.config.OrderDetailServiceConfiguration;
import com.integration.ext.demo.webservice.client.Order;
import com.integration.ext.demo.webservice.client.OrderDetail;
import com.integration.ext.demo.webservice.client.OrderDetailRequest;
import com.integration.ext.demo.webservice.client.OrderDetailResponse;
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
public class OrderDetailIntegrationApplicationTests {

	private Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

	@LocalServerPort
	private int port = 0;

	private String uri;

	@Before
	public void init() throws Exception {
		marshaller.setPackagesToScan(ClassUtils.getPackageName(OrderDetailRequest.class));
		marshaller.afterPropertiesSet();
		uri = "http://localhost:" + port + OrderDetailServiceConfiguration.uriPath;
	}
	@Test
	public void contextLoads() {
	}

	@Test
	public void orderDetailSendAndReceive() {
		WebServiceTemplate template = new WebServiceTemplate(marshaller);
		OrderDetailRequest request = new OrderDetailRequest();
		Order order = new Order();
		String orderId = "123";
		order.setId(orderId);
		request.setOrder(order);
		Object respObject = template.marshalSendAndReceive(uri, request);
		assertThat(respObject).isNotNull();
		assertThat(respObject instanceof OrderDetailResponse).isTrue();
		OrderDetailResponse resp = (OrderDetailResponse)respObject;
		OrderDetail orderResp = resp.getOrderDetail();
		assertThat(orderResp).isNotNull();
		assertThat(orderResp.getId().equals(orderId));
		assertThat(orderResp.getTotal()).isNotBlank();
	}


}
