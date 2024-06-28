package com.sweelam;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class XMockApplication {
    public static void main(String[] args) {

       SpringApplication.run(XMockApplication.class, args);

    }
}