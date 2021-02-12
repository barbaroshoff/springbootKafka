package com.example.kafka.Springbootkafka.config;

import com.example.kafka.Springbootkafka.config.SOAPDecoder;
import com.example.kafka.Springbootkafka.config.SOAPEncoder;
import feign.codec.Decoder;
import feign.codec.Encoder;

import feign.jaxb.JAXBContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    private static final JAXBContextFactory jaxbFactory = new JAXBContextFactory.Builder()
            .withMarshallerJAXBEncoding("UTF-8")
            .withMarshallerSchemaLocation("/home/java/IdeaProjects/Springboot-kafka/src/main/resources/data.xsd")
            .build();

    @Bean
    public Encoder feignEncoder() {
        return new SOAPEncoder(jaxbFactory);
    }
    @Bean
    public Decoder feignDecoder() {
        return new SOAPDecoder(jaxbFactory);
    }
}
