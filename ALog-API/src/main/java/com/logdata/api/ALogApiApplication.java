package com.logdata.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.logdata")
public class ALogApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ALogApiApplication.class, args);
    }
}
