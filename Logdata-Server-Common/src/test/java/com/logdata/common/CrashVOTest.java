package com.logdata.common;

import com.logdata.common.model.CrashTimeVO;
import com.logdata.common.model.CrashVO;
import com.logdata.common.repository.CrashDataRepository;
import com.logdata.common.util.Utility;
import org.joda.time.DateTime;
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
public class CrashVOTest {
    @MockBean
    private CrashDataRepository crashDataRepository;
    @Autowired
    private Utility utility;

    @Test
    public void contextLoads() {
    }

    @Test
    public void crashDataTest() {
        CrashVO crashVO1 = new CrashVO();
        crashVO1.setPackageName("android");
        crashVO1.setTime(1L);
        crashVO1.setAndroidVersion("5.0");
        crashVO1.setAppVersionCode("1.0");
        crashVO1.setAppVersionName("1.0");
        crashVO1.setAvailableMemorySize(1L);
        crashVO1.setBrand("samsung");
        crashVO1.setLogcat("logcat");
        crashVO1.setDeviceID("deviceID");
        crashVO1.setDisplay(new HashMap<>());
        crashVO1.setEnvironment(new HashMap<>());
        crashVO1.setDeviceFeatures(new HashMap<>());
        crashVO1.setBuild(new HashMap<>());
        crashVO1.setApiKey("key");

        when(crashDataRepository.findOne("1")).thenReturn(crashVO1);
        CrashVO result1 = this.crashDataRepository.findOne("1");
        assertThat(result1.getPackageName()).isEqualTo(crashVO1.getPackageName());
        assertThat(result1.getTime()).isEqualTo(crashVO1.getTime());
        assertThat(result1.getAndroidVersion()).isEqualTo(crashVO1.getAndroidVersion());
        assertThat(result1.getAppVersionCode()).isEqualTo(crashVO1.getAppVersionCode());
        assertThat(result1.getAppVersionName()).isEqualTo(crashVO1.getAppVersionName());
        assertThat(result1.getAvailableMemorySize()).isEqualTo(crashVO1.getAvailableMemorySize());
        assertThat(result1.getBrand()).isEqualTo(crashVO1.getBrand());
        assertThat(result1.getLogcat()).isEqualTo(crashVO1.getLogcat());
        assertThat(result1.getDeviceID()).isEqualTo(crashVO1.getDeviceID());
        assertThat(result1.getDisplay()).isEqualTo(crashVO1.getDisplay());
        assertThat(result1.getEnvironment()).isEqualTo(crashVO1.getEnvironment());
        assertThat(result1.getDeviceFeatures()).isEqualTo(crashVO1.getDeviceFeatures());
        assertThat(result1.getBuild()).isEqualTo(crashVO1.getBuild());
        assertThat(result1.getApiKey()).isEqualTo(crashVO1.getApiKey());
        assertThat(result1.toString()).isEqualTo("{\"packageName\":\"android\",\"time\":1,\"androidVersion\":\"5.0\",\"appVersionCode\":\"1.0\",\"appVersionName\":\"1.0\",\"availableMemorySize\":1,\"brand\":\"samsung\",\"logcat\":\"logcat\",\"deviceID\":\"deviceID\",\"display\":{},\"environment\":{},\"deviceFeatures\":{},\"build\":{},\"apiKey\":\"key\"}");

        CrashVO crashVO2 = new CrashVO("android", 1L, "5.0", "1.0", "1.0", 1L, "samsung", "logcat", "deviceID", new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), "key");
        when(crashDataRepository.findOne("1")).thenReturn(crashVO2);
        CrashVO result2 = this.crashDataRepository.findOne("1");
        assertThat(result2.getPackageName()).isEqualTo(crashVO2.getPackageName());
        assertThat(result2.getTime()).isEqualTo(crashVO2.getTime());
        assertThat(result2.getAndroidVersion()).isEqualTo(crashVO2.getAndroidVersion());
        assertThat(result2.getAppVersionCode()).isEqualTo(crashVO2.getAppVersionCode());
        assertThat(result2.getAppVersionName()).isEqualTo(crashVO2.getAppVersionName());
        assertThat(result2.getAvailableMemorySize()).isEqualTo(crashVO2.getAvailableMemorySize());
        assertThat(result2.getBrand()).isEqualTo(crashVO2.getBrand());
        assertThat(result2.getLogcat()).isEqualTo(crashVO2.getLogcat());
        assertThat(result2.getDeviceID()).isEqualTo(crashVO2.getDeviceID());
        assertThat(result2.getDisplay()).isEqualTo(crashVO2.getDisplay());
        assertThat(result2.getEnvironment()).isEqualTo(crashVO2.getEnvironment());
        assertThat(result2.getDeviceFeatures()).isEqualTo(crashVO2.getDeviceFeatures());
        assertThat(result2.getBuild()).isEqualTo(crashVO2.getBuild());
        assertThat(result2.getApiKey()).isEqualTo(crashVO2.getApiKey());
        assertThat(result2.toString()).isEqualTo("{\"packageName\":\"android\",\"time\":1,\"androidVersion\":\"5.0\",\"appVersionCode\":\"1.0\",\"appVersionName\":\"1.0\",\"availableMemorySize\":1,\"brand\":\"samsung\",\"logcat\":\"logcat\",\"deviceID\":\"deviceID\",\"display\":{},\"environment\":{},\"deviceFeatures\":{},\"build\":{},\"apiKey\":\"key\"}");
    }

    @Test
    public void CrashTimeVOTest() {
        CrashVO crashVO = new CrashVO();
        crashVO.setTime(0L);
        crashVO.setPackageName("android");
        CrashTimeVO crashTimeVO1 = new CrashTimeVO();
        crashTimeVO1.setTime(0L);
        crashTimeVO1.setPackageName("android");
        crashTimeVO1.setStringTime("1970-01-01 00:00:00.000");

        when(crashDataRepository.findCrashDataByTimeAndApiKey(new DateTime(0), "key")).thenReturn(crashVO);
        CrashVO result1 = crashDataRepository.findCrashDataByTimeAndApiKey(new DateTime(0), "key");
        assertThat(result1.getTime()).isEqualTo(crashTimeVO1.getTime());
        assertThat(result1.getPackageName()).isEqualTo(crashTimeVO1.getPackageName());
//        assertThat(utility.getStringTimeToDateTime(new DateTime(result1.getTime()))).isEqualTo(crashTimeVO1.getStringTime());
        assertThat(crashTimeVO1.toString()).isEqualTo("{\"Time\":0,\"PackageName\":\"android\",\"StringTime\":\"1970-01-01 00:00:00.000\"}");

        CrashTimeVO crashTimeVO2 = new CrashTimeVO(0L, "android", "1970-01-01 00:00:00.000");
        when(crashDataRepository.findCrashDataByTimeAndApiKey(new DateTime(0), "key")).thenReturn(crashVO);
        CrashVO result2 = crashDataRepository.findCrashDataByTimeAndApiKey(new DateTime(0), "key");
        assertThat(result2.getTime()).isEqualTo(crashTimeVO2.getTime());
        assertThat(result2.getPackageName()).isEqualTo(crashTimeVO2.getPackageName());
//        assertThat(utility.getStringTimeToDateTime(new DateTime(result2.getTime()))).isEqualTo(crashTimeVO2.getStringTime());
        assertThat(crashTimeVO2.toString()).isEqualTo("{\"Time\":0,\"PackageName\":\"android\",\"StringTime\":\"1970-01-01 00:00:00.000\"}");
    }
}
