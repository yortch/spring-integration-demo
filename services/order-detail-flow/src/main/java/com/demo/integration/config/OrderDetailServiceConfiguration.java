package com.demo.integration.config;

import com.integration.ext.demo.webservice.client.OrderDetailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.ws.MarshallingWebServiceInboundGateway;
import org.springframework.messaging.MessageChannel;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.ClassUtils;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;

@EnableWs
@Configuration
public class OrderDetailServiceConfiguration extends WsConfigurerAdapter {

    private static Logger LOG = LoggerFactory.getLogger(OrderDetailServiceConfiguration.class);

    public static final String uriPath = "/orderDetailService";

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, uriPath + "/*");
    }

    /**
     * TODO: do not like having to include the wsdl, but do not see a way around this,
     * another alternative is to include the xsd only and set the port type programmatically
     */
    @Bean (name="orderDetail")
    public SimpleWsdl11Definition orderWdlDefinition() {
        SimpleWsdl11Definition definition = new SimpleWsdl11Definition();
        definition.setWsdl(new ClassPathResource("/schemas/orderDetail.wsdl"));

        return definition;
    }

    @Bean
    Jaxb2Marshaller orderDetailServiceMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(ClassUtils.getPackageName(OrderDetailRequest.class));
        return marshaller;
    }

    /**
     * Message channel used to handle message when the web service gateway is invoked
     */
    @Bean
    public MessageChannel orderDetailInputChannel() {
        return new DirectChannel();
    }

    @Bean
    MarshallingWebServiceInboundGateway orderDetailInboundGateway() {
        MarshallingWebServiceInboundGateway inboundGateway = new MarshallingWebServiceInboundGateway();
        Jaxb2Marshaller orderServiceMarshaller = orderDetailServiceMarshaller();
        inboundGateway.setMarshaller(orderServiceMarshaller);
        inboundGateway.setUnmarshaller(orderServiceMarshaller);
        inboundGateway.setRequestChannel(orderDetailInputChannel());
        return inboundGateway;
    }
}
