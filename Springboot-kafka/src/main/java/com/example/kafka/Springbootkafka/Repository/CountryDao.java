package com.example.kafka.Springbootkafka.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


public interface CountryDao extends JpaRepository<CountryEntity, String> {
    CountryEntity findByCapital(String capital);

    CountryEntity findByName(String name);

    CountryEntity findByCurrency(String currency);

    CountryEntity findByPopulation(int population);
}
