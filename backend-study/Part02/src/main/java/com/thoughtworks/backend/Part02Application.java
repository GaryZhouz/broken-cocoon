package com.thoughtworks.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class Part02Application {

    public static void main(String[] args) {
        SpringApplication.run(Part02Application.class, args);
    }

}
