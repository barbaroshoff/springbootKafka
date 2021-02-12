package com.example.kafka.Springbootkafka.service;


import com.example.kafka.Springbootkafka.Repository.CountryDao;
import com.example.kafka.Springbootkafka.Repository.CountryEntity;
import com.example.kafka.Springbootkafka.mypackage.GetCountryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Data
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<Long, GetCountryResponse> kafkaTemplate;
    private final ObjectMapper objectMapper;



    @Autowired
    ClientFeign clientFeign;

    @Autowired
    private CountryDao countryDao;

    @Autowired
    public KafkaServiceImpl(KafkaTemplate<Long, GetCountryResponse> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }



    @Override
    public void send(GetCountryResponse message) {
        log.info("<= sending {}",message);
        kafkaTemplate.send("server.starship", message);
    }

    @Override
    @KafkaListener(id = "Starship", topics = {"server.starship"}, containerFactory = "singleFactory")
    public void consume(GetCountryResponse message){
        log.info("consuming => {}",message.toString());
        CountryEntity countryEntity = new CountryEntity();

        //Deserialize from string Xml to Dto

        //set data to entity
        countryEntity.setName(message.getCountry().getName());
        countryEntity.setCapital(message.getCountry().getCapital());
        countryEntity.setCurrency(message.getCountry().getCurrency().toString());
        countryEntity.setPopulation( message.getCountry().getPopulation());

        //save entity to database

        countryDao.save(countryEntity);

        clientFeign.sendData(message);
    }




}

