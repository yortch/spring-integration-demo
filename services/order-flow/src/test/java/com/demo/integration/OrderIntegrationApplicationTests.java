package com.demo.integration;

import com.integration.demo.webservice.client.Order;
import com.integration.demo.webservice.client.OrderRequest;
import com.integration.demo.webservice.client.OrderResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.integration.ws.MarshallingWebServiceOutboundGateway;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ClassUtils;
import org.springframework.ws.InvalidXmlException;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.WebServiceConnection;
import org.springframework.ws.transport.WebServiceMessageSender;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderIntegrationApplicationTests {

	private Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

	@Autowired
	@Qualifier("orderDetailOutboundGateway")
	private MarshallingWebServiceOutboundGateway orderDetailOutboundGateway;

	@Mock
	private WebServiceMessageSender messageSender;

	@Mock
	private WebServiceConnection wsConnection;

	@LocalServerPort
	private int port = 0;

	private String uri;

	private static final String XML_REPONSE = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
			"   <SOAP-ENV:Header/>\n" +
			"   <SOAP-ENV:Body>\n" +
			"      <ns2:orderDetailResponse xmlns:ns2=\"http://ext.demo/schemas\">\n" +
			"         <ns2:orderDetail>\n" +
			"            <ns2:id>6</ns2:id>\n" +
			"            <ns2:total>1734718007</ns2:total>\n" +
			"         </ns2:orderDetail>\n" +
			"      </ns2:orderDetailResponse>\n" +
			"   </SOAP-ENV:Body>\n" +
			"</SOAP-ENV:Envelope>";

	@Before
	public void init() throws Exception {
		marshaller.setPackagesToScan(ClassUtils.getPackageName(OrderRequest.class));
		marshaller.afterPropertiesSet();
		uri = "http://localhost:" + port + "/orderService";

		// mocking the WS SOAP gateway
		when(this.messageSender.createConnection(any(URI.class))).thenReturn(this.wsConnection);
		when(this.messageSender.supports(any(URI.class))).thenReturn(true);

		// the gateway will always respond with a static response
		doAnswer(new Answer<WebServiceMessage>() {
			public WebServiceMessage answer(InvocationOnMock invocation) throws InvalidXmlException, IOException {
				WebServiceMessageFactory factory = invocation.getArgument (0);
				return factory.createWebServiceMessage(new ByteArrayInputStream(XML_REPONSE.getBytes()));
			}
		}).when(this.wsConnection).receive(any(WebServiceMessageFactory.class));

		this.orderDetailOutboundGateway.setMessageSender(this.messageSender);
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
