package com.logdata.api;

import com.logdata.common.repository.CrashDataRepository;
import com.logdata.common.repository.LogDataRepository;
import com.logdata.common.repository.PackageNameDataRepository;
import com.logdata.common.repository.UserDataRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan("com.logdata")
@EnableMongoRepositories(
        basePackageClasses = {
                UserDataRepository.class,
                LogDataRepository.class,
                CrashDataRepository.class,
                PackageNameDataRepository.class})
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
