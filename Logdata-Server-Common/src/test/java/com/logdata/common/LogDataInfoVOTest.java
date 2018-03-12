package com.logdata.common;

import com.logdata.common.model.LogDataInfoVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LogDataInfoVOTest {
    @Test
    public void mainPageVOTest() {
        LogDataInfoVO logDataInfoVO1 = new LogDataInfoVO();
        logDataInfoVO1.setPackageName("android");
        logDataInfoVO1.setRecentCrashTime("1970-01-01 09:00:00.000");
        logDataInfoVO1.setVerb(0);
        logDataInfoVO1.setInfo(1);
        logDataInfoVO1.setDebug(2);
        logDataInfoVO1.setWarning(3);
        logDataInfoVO1.setError(4);

        assertThat(logDataInfoVO1.getPackageName()).isEqualTo("android");
        assertThat(logDataInfoVO1.getRecentCrashTime()).isEqualTo("1970-01-01 09:00:00.000");
        assertThat(logDataInfoVO1.getVerb()).isEqualTo(0);
        assertThat(logDataInfoVO1.getInfo()).isEqualTo(1);
        assertThat(logDataInfoVO1.getDebug()).isEqualTo(2);
        assertThat(logDataInfoVO1.getWarning()).isEqualTo(3);
        assertThat(logDataInfoVO1.getError()).isEqualTo(4);
        assertThat(logDataInfoVO1.toString()).isEqualTo("{\"packageName\":\"android\",\"RecentCrashTime\":\"1970-01-01 09:00:00.000\",\"Verb\":0,\"Info\":1,\"Debug\":2,\"Warning\":3,\"Error\":4}");

        LogDataInfoVO logDataInfoVO2 = new LogDataInfoVO("android", "1970-01-01 12:34:56.000", 0, 1, 2, 3, 4);

        assertThat(logDataInfoVO2.toString()).isEqualTo("{\"packageName\":\"android\",\"RecentCrashTime\":\"1970-01-01 12:34:56.000\",\"Verb\":0,\"Info\":1,\"Debug\":2,\"Warning\":3,\"Error\":4}");
    }
}