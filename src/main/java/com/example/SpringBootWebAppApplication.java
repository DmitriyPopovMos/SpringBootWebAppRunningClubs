package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication                                                   // @SpringBootApplication — Класс, аннотированный @SpringBootApplication, является основным классом приложения Spring Boot, с него начинается работа приложения.
public class SpringBootWebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebAppApplication.class, args);  // SpringApplication.run() — это статический метод класса SpringApplication, который запускает приложение Spring при старте
    }

}
