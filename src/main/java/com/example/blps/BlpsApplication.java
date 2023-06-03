package com.example.blps;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Configuration
@EnableJms
@EnableTransactionManagement
@EnableProcessApplication
public class BlpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlpsApplication.class, args);
    }

}
