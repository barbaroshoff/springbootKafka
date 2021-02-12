package com.example.kafka.Springbootkafka.Repository;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class CountryEntity {
   @Id
   @GeneratedValue
   private int id;

   @Column(name = "countryName", nullable = false)
   private String name;

   @Column(name = "countryCapital", nullable = false)
   private String capital;

   @Column(name = "countryCurrency", nullable = false)
   private String currency;

   @Column(name = "countryPopulation", nullable = false)
   private int population;
}
