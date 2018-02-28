package com.logdata.backend;

import com.logdata.common.repository.CrashDataRepository;
import com.logdata.common.repository.LogDataRepository;
import com.logdata.common.repository.UserDataRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan("com.logdata")
@EnableMongoRepositories(basePackageClasses = {UserDataRepository.class, LogDataRepository.class, CrashDataRepository.class})
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}
