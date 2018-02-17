package com.logdata.backend;

import com.logdata.backend.model.LogVO;
import com.logdata.backend.repository.CrashDataRepository;
import com.logdata.backend.repository.LogDataRepository;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BackendApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
    private LogDataRepository logDataRepository;
	@Autowired
    private CrashDataRepository crashDataRepository;

    @Test
    public void logDataInputTest() {
        long currentTime = System.currentTimeMillis();
        this.logDataRepository.save(new LogVO("1", "android", "i", "[MainActivity::onCreate]", "Hello, World!", new DateTime(currentTime), null ,"apiKey"));

        LogVO log = this.logDataRepository.findOne("1");

        org.assertj.core.api.Assertions.assertThat(log.getId()).isEqualTo("1");
        org.assertj.core.api.Assertions.assertThat(log.getPackageName()).isEqualTo("android");
        org.assertj.core.api.Assertions.assertThat(log.getLevel()).isEqualTo("i");
        org.assertj.core.api.Assertions.assertThat(log.getTag()).isEqualTo("[MainActivity::onCreate]");
        org.assertj.core.api.Assertions.assertThat(log.getMessage()).isEqualTo("Hello, World!");
        org.assertj.core.api.Assertions.assertThat(log.getTime()).isEqualTo(new DateTime(currentTime));
        org.assertj.core.api.Assertions.assertThat(log.getMemoryInfo()).isEqualTo(null);
        org.assertj.core.api.Assertions.assertThat(log.getApiKey()).isEqualTo("apiKey");
    }
}
