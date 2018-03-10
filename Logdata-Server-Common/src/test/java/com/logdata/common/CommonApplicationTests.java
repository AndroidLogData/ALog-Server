package com.logdata.common;

import com.logdata.common.repository.CrashDataRepository;
import com.logdata.common.repository.LogDataRepository;
import com.logdata.common.repository.UserDataRepository;
import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {LogDataRepository.class, CrashDataRepository.class, UserDataRepository.class})
public class CommonApplicationTests {
    @Test
    public void contextLoads() {}
}
