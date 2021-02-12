package com.example.kafka.Springbootkafka;


import com.example.kafka.Springbootkafka.Repository.CountryDao;
import com.example.kafka.Springbootkafka.Repository.CountryEntity;
import com.example.kafka.Springbootkafka.mypackage.Country;
import com.example.kafka.Springbootkafka.mypackage.Currency;
import com.example.kafka.Springbootkafka.mypackage.GetCountryResponse;
import com.example.kafka.Springbootkafka.service.KafkaServiceImpl;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = KafkaServerApplication.class,  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
public class SoapTestExample {
    private  String BASE_URL = "http://localhost:";

    @Autowired
    private CountryDao countryDao;

    @Autowired
    KafkaServiceImpl kafkaService;

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    public void init(){
        BASE_URL += serverPort;
    }


    @Test
    void whenSaved_thenFindsByName() {
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setCurrency("hello");
        countryEntity.setCapital("Moldova");
        countryEntity.setName("Madrid");
        countryEntity.setPopulation(23243);
        countryDao.save(countryEntity);
        assertThat(countryDao.findByCapital("Moldova"),is(notNullValue()));
        assertThat(countryDao.findByCurrency("hello").getCurrency(),equalTo(countryEntity.getCurrency()));
        assertThat(countryDao.findByName("Madrid").getName(),equalTo(countryEntity.getName()));
        assertThat(countryDao.findByPopulation(23243).getPopulation(),equalTo(countryEntity.getPopulation()));
        assertThat(countryDao.findByCapital("Moldova").getCapital(),equalTo(countryEntity.getCapital()));

    }


    @Test
    void testKafkaConsumer() {
        GetCountryResponse getCountryResponse = new GetCountryResponse();
        Country country = new Country();
        country.setCapital("Rom");
        country.setCurrency(Currency.EUR);
        country.setName("Romania");
        country.setPopulation(400);
        getCountryResponse.setCountry(country);

        kafkaService.send(getCountryResponse);

        assertThat(countryDao.findAll(),is(notNullValue()));
    }

    @Test
    public void valid_xsd_request_response_test() throws UnirestException {
        String req =
                "<env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\"\nxmlns:gs=\"http://spring.io/guides/gs-producing-web-service\">\n" +
                "<env:Header/>\n" +
                "<env:Body>\n" +
                "<gs:getCountryResponse>\n" +
                "<gs:country>\n " +
                "   <gs:name>Spain</gs:name>\n" +
                "    <gs:population>46704314</gs:population>\n" +
                "    <gs:capital>Madrid</gs:capital>\n   " +
                "    <gs:currency>EUR</gs:currency>\n" +
                "</gs:country>\n" +
                "</gs:getCountryResponse>\n" +
                "</env:Body>\n" +
                "</env:Envelope>";

        System.out.println(BASE_URL);
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(BASE_URL+"/endpoint")
                .header("Content-Type", "application/soap+xml")
                .body(req)
                .asString();

        //check if response is not null
        assertThat(response.getBody(),is(notNullValue()));

        //check if content type equals
        assertThat(
                response.getHeaders().get("Content-Type").get(0).trim()
                ,equalTo("application/soap+xml;charset=utf-8".trim())
        );

        //check status code
        assertThat(response.getStatus(),is(200));
    }


    @Test
    public void fail_custom_request_response_test() throws UnirestException {
        String req = "hello";

        System.out.println(BASE_URL);
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(BASE_URL+"/endpoint")
                .header("Content-Type", "application/json")
                .body(req)
                .asString();

        //check if response is not null
        assertThat(response.getBody(),is(notNullValue()));

        //check if content type is invalid

        //check error status code
        assertThat(response.getStatus(),is(500));
    }

}
