package com.logdata.api;

import com.logdata.common.logger.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.logdata")
public class ALogApiApplication {
    public static void main(String[] args) {
        Logger.showLog(true);
        SpringApplication.run(ALogApiApplication.class, args);
    }
}
