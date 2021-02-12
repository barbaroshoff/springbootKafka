package com.example.kafka.Springbootkafka.service;


import com.example.kafka.Springbootkafka.Repository.CountryEntity;
import com.example.kafka.Springbootkafka.config.FeignConfig;
import com.example.kafka.Springbootkafka.mypackage.GetCountryResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value ="test",url="http://localhost:8080/feign",configuration = FeignConfig.class)
public interface ClientFeign {
    @RequestMapping(method= RequestMethod.POST ,value="/testData" ,consumes = MediaType.TEXT_XML_VALUE,produces = MediaType.TEXT_XML_VALUE)
    ResponseEntity sendData(@RequestBody GetCountryResponse getCountryResponse);
}
