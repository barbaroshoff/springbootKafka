package com.example.kafka.Springbootkafka.controller;

import com.example.kafka.Springbootkafka.mypackage.GetCountryResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign")
public class FeignController {
    @PostMapping(path = "/testData", consumes = MediaType.TEXT_XML_VALUE,produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<String> feignData(@RequestBody GetCountryResponse getCountryResponse){
        System.out.println(getCountryResponse+"This is how Feign Works");

        return ResponseEntity.ok("This is how Feign Works");
    }
}
