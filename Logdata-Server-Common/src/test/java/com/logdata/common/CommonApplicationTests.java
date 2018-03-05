package com.logdata.common;

import com.logdata.common.model.LogVO;
import com.logdata.common.repository.LogDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CommonApplicationTests {
    @MockBean
    private LogDataRepository logDataRepository;

	@Test
	public void contextLoads() {
	}

    @Test
    public void LogDataFindOneTest() {
        LogVO logVO = new LogVO("1", "android", "v", "MainActivity", "Hello", 1L, null, "key");
        when(logDataRepository.findOne("1")).thenReturn(logVO);
        LogVO temp = logDataRepository.findOne("1");
        assertThat(temp.getId()).isEqualTo(logVO.getId());
        assertThat(temp.getPackageName()).isEqualTo(logVO.getPackageName());
        assertThat(temp.getLevel()).isEqualTo(logVO.getLevel());
        assertThat(temp.getTag()).isEqualTo(logVO.getTag());
        assertThat(temp.getMessage()).isEqualTo(logVO.getMessage());
        assertThat(temp.getTime()).isEqualTo(logVO.getTime());
        assertThat(temp.getMemoryInfo()).isEqualTo(logVO.getMemoryInfo());
        assertThat(temp.getApiKey()).isEqualTo(logVO.getApiKey());
    }

    @Test
    public void LogDataSaveTest() {
        LogVO logVO = new LogVO("1", "android", "v", "MainActivity", "Hello", 1L, null, "key");
        when(logDataRepository.save(logVO)).thenReturn(logVO);
        LogVO temp = logDataRepository.save(logVO);
        assertThat(temp.getId()).isEqualTo(logVO.getId());
        assertThat(temp.getPackageName()).isEqualTo(logVO.getPackageName());
        assertThat(temp.getLevel()).isEqualTo(logVO.getLevel());
        assertThat(temp.getTag()).isEqualTo(logVO.getTag());
        assertThat(temp.getMessage()).isEqualTo(logVO.getMessage());
        assertThat(temp.getTime()).isEqualTo(logVO.getTime());
        assertThat(temp.getMemoryInfo()).isEqualTo(logVO.getMemoryInfo());
        assertThat(temp.getApiKey()).isEqualTo(logVO.getApiKey());
    }
}

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {LogDataRepository.class})
class ModuleCommonApplicationTests {
    public void contextLoads() {}
}
