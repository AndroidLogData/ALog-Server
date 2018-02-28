package com.logdata.backend;

import com.logdata.common.model.CrashVO;
import com.logdata.common.model.LogVO;
import com.logdata.common.repository.CrashDataRepository;
import com.logdata.common.repository.LogDataRepository;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BackendApplicationTests {

	@Test
	public void contextLoads() {
	}

	@MockBean
    private LogDataRepository logDataRepository;
    @MockBean
    private CrashDataRepository crashDataRepository;

    @Test
    public void logDataInputTest() {
        long currentTime = System.currentTimeMillis();
        LogVO log = new LogVO("1", "android", "i", "[MainActivity::onCreate]", "Hello, World!", new DateTime(currentTime), null ,"apiKey");

        given(logDataRepository.findOne("1")).willReturn(log);

        assertThat(log.getId()).isEqualTo("1");
        assertThat(log.getPackageName()).isEqualTo("android");
        assertThat(log.getLevel()).isEqualTo("i");
        assertThat(log.getTag()).isEqualTo("[MainActivity::onCreate]");
        assertThat(log.getMessage()).isEqualTo("Hello, World!");
        assertThat(log.getTime()).isEqualTo(new DateTime(currentTime));
        assertThat(log.getMemoryInfo()).isEqualTo(null);
        assertThat(log.getApiKey()).isEqualTo("apiKey");
    }

    @Test
    public void crashDataInputTest() {
        long currentTime = System.currentTimeMillis();
        HashMap<String, Object> display = new HashMap<String, Object>();
        HashMap<String, Object> deviceFeature = new HashMap<String, Object>();
        HashMap<String, Object> environment = new HashMap<String, Object>();
        HashMap<String, Object> build = new HashMap<String, Object>();

        CrashVO crashVO = new CrashVO("1", "android", new DateTime(currentTime), "5.0", "1", "1.0", 1L, "samsung", "logcat", "deviceID", display, environment, deviceFeature, build, "apiKey");

        given(crashDataRepository.findOne("1")).willReturn(crashVO);

        assertThat(crashVO.getId()).isEqualTo("1");
        assertThat(crashVO.getPackageName()).isEqualTo("android");
        assertThat(crashVO.getTime()).isEqualTo(new DateTime(currentTime));
        assertThat(crashVO.getAndroidVersion()).isEqualTo("5.0");
        assertThat(crashVO.getAppVersionCode()).isEqualTo("1");
        assertThat(crashVO.getAppVersionName()).isEqualTo("1.0");
        assertThat(crashVO.getAvailableMemorySize()).isEqualTo(1L);
        assertThat(crashVO.getBrand()).isEqualTo("samsung");
        assertThat(crashVO.getLogcat()).isEqualTo("logcat");
        assertThat(crashVO.getDeviceID()).isEqualTo("deviceID");
        assertThat(crashVO.getDisplay()).isEqualTo(display);
        assertThat(crashVO.getEnvironment()).isEqualTo(environment);
        assertThat(crashVO.getDeviceFeatures()).isEqualTo(deviceFeature);
        assertThat(crashVO.getBuild()).isEqualTo(build);
    }
}
