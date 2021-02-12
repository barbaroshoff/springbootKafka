package com.example.kafka.Springbootkafka.controller;


import com.example.kafka.Springbootkafka.mypackage.GetCountryResponse;
import com.example.kafka.Springbootkafka.service.KafkaServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CountryEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    @Autowired
    KafkaServiceImpl kafkaService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryResponse")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryResponse request) throws JsonProcessingException {
        kafkaService.send(request);

        return request;
    }
}
