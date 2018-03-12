package com.logdata.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LogDataInfoVO {
    @Test
    public void mainPageVOTest() {
        com.logdata.common.model.LogDataInfoVO logDataInfoVO = new com.logdata.common.model.LogDataInfoVO();
        logDataInfoVO.setPackageName("android");
        logDataInfoVO.setRecentCrashTime("1970-01-01 09:00:00.000");
        logDataInfoVO.setVerb(0);
        logDataInfoVO.setInfo(1);
        logDataInfoVO.setDebug(2);
        logDataInfoVO.setWarning(3);
        logDataInfoVO.setError(5);

        assertThat(logDataInfoVO.getPackageName()).isEqualTo(logDataInfoVO.getPackageName());
        assertThat(logDataInfoVO.getRecentCrashTime()).isEqualTo(logDataInfoVO.getRecentCrashTime());
        assertThat(logDataInfoVO.getVerb()).isEqualTo(logDataInfoVO.getVerb());
        assertThat(logDataInfoVO.getInfo()).isEqualTo(logDataInfoVO.getInfo());
        assertThat(logDataInfoVO.getDebug()).isEqualTo(logDataInfoVO.getDebug());
        assertThat(logDataInfoVO.getWarning()).isEqualTo(logDataInfoVO.getWarning());
        assertThat(logDataInfoVO.getError()).isEqualTo(logDataInfoVO.getError());
        assertThat(logDataInfoVO.toString()).isEqualTo("{\"packageName\":\"android\",\"RecentCrashTime\":\"1970-01-01 09:00:00.000\",\"Verb\":0,\"Info\":1,\"Debug\":2,\"Warning\":3,\"Error\":5}");
    }
}
