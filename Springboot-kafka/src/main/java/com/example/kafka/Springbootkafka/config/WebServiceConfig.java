package com.example.kafka.Springbootkafka.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import javax.xml.soap.SOAPException;
import java.sql.SQLException;

@EnableWs
@Configuration
@ComponentScan
@EnableTransactionManagement
public class WebServiceConfig extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        final MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/endpoint/*");
    }

    @Bean
    public SaajSoapMessageFactory messageFactory() throws SOAPException {
        final SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
        messageFactory.setSoapVersion(SoapVersion.SOAP_12);
        return messageFactory;
    }

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("mypackage");
        return marshaller;
    }

    @Bean
    public SOAPConnector soapConnector(Jaxb2Marshaller marshaller) throws SOAPException {
        final SOAPConnector client = new SOAPConnector();

        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        client.setMessageSender(webServiceMessageSender());
        client.setMessageFactory(messageFactory());

        return client;
    }


    @Bean
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema) throws SQLException {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setCreateSoap12Binding(true);
        definition.setPortTypeName("CountryPort");
        definition.setLocationUri("/endpoint/*");
        definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
        definition.setSchema(schema);
        return definition;
    }

    @Bean
    public XsdSchema schema(){
        return new SimpleXsdSchema(new ClassPathResource("data.xsd"));
    }


    @Bean
    public WebServiceMessageSender webServiceMessageSender() {
        final HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        // timeout for creating a connection
        httpComponentsMessageSender.setConnectionTimeout(30000);
        // when you have a connection, timeout the read blocks for
        httpComponentsMessageSender.setReadTimeout(30000);

        return httpComponentsMessageSender;
    }

}
