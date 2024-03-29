package com.example.blps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Configuration
@EnableJms
@EnableTransactionManagement
public class BlpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlpsApplication.class, args);
    }

}
