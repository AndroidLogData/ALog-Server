package com.logdata.common;

import com.logdata.common.model.LogVO;
import com.logdata.common.repository.LogDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LogVOTest {
    @MockBean
    private LogDataRepository logDataRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void LogDataFindOneTest() {
        LogVO logVO1 = new LogVO();
        LogVO logVO2 = new LogVO("android", "v", "MainActivity", "Hello", 1L, null, "key");

        when(logDataRepository.findOne("1")).thenReturn(logVO1);
        LogVO result1 = logDataRepository.findOne("1");
        assertThat(result1.getPackageName()).isEqualTo(null);
        assertThat(result1.getLevel()).isEqualTo(null);
        assertThat(result1.getTag()).isEqualTo(null);
        assertThat(result1.getMessage()).isEqualTo(null);
        assertThat(result1.getTime()).isEqualTo(0L);
        assertThat(result1.getMemoryInfo()).isEqualTo(null);
        assertThat(result1.getApiKey()).isEqualTo(null);
        assertThat(result1.toString()).isEqualTo("{\"packageName\":\"null\",\"Level\":\"null\",\"Tag\":\"null\",\"Message\":\"null\",\"Time\":0,\"MemoryInfo\":null,\"ApiKey\":\"null\"}");

        when(logDataRepository.findOne("1")).thenReturn(logVO2);
        LogVO result2 = logDataRepository.findOne("1");
        assertThat(result2.getPackageName()).isEqualTo(logVO2.getPackageName());
        assertThat(result2.getLevel()).isEqualTo(logVO2.getLevel());
        assertThat(result2.getTag()).isEqualTo(logVO2.getTag());
        assertThat(result2.getMessage()).isEqualTo(logVO2.getMessage());
        assertThat(result2.getTime()).isEqualTo(logVO2.getTime());
        assertThat(result2.getMemoryInfo()).isEqualTo(logVO2.getMemoryInfo());
        assertThat(result2.getApiKey()).isEqualTo(logVO2.getApiKey());
        assertThat(result2.toString()).isEqualTo("{\"packageName\":\"android\",\"Level\":\"v\",\"Tag\":\"MainActivity\",\"Message\":\"Hello\",\"Time\":1,\"MemoryInfo\":null,\"ApiKey\":\"key\"}");
    }

    @Test
    public void LogDataSaveTest() {
        LogVO logVO = new LogVO();
        logVO.setPackageName("android");
        logVO.setLevel("v");
        logVO.setTag("[MainActivity::onCreate]");
        logVO.setMessage("Hello, World!");
        logVO.setTime(1L);
        logVO.setMemoryInfo(new HashMap<String, Object>());
        logVO.setApiKey("key");

        when(logDataRepository.save(logVO)).thenReturn(logVO);
        LogVO result = logDataRepository.save(logVO);
        assertThat(result.getPackageName()).isEqualTo(logVO.getPackageName());
        assertThat(result.getLevel()).isEqualTo(logVO.getLevel());
        assertThat(result.getTag()).isEqualTo(logVO.getTag());
        assertThat(result.getMessage()).isEqualTo(logVO.getMessage());
        assertThat(result.getTime()).isEqualTo(logVO.getTime());
        assertThat(result.getMemoryInfo()).isEqualTo(logVO.getMemoryInfo());
        assertThat(result.getApiKey()).isEqualTo(logVO.getApiKey());
    }
}
