package com.example.kafka.Springbootkafka.service;

import com.example.kafka.Springbootkafka.mypackage.GetCountryResponse;

import java.util.List;

public interface KafkaService {

    void send(GetCountryResponse message);

    void consume(GetCountryResponse message);

}

