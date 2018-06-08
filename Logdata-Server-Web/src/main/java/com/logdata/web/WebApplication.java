package com.logdata.web;

import com.logdata.common.logger.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.logdata")
public class WebApplication {

	public static void main(String[] args) {
        Logger.showLog(true);
		SpringApplication.run(WebApplication.class, args);
	}
}
