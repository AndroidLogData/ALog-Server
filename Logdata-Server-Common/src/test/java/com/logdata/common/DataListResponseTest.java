package com.logdata.common;

import com.logdata.common.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DataListResponseTest {
    @Test
    public void LogDataListResponseTest() {
        ArrayList<LogVO> logVOS = new ArrayList<LogVO>();
        logVOS.add(new LogVO());
        LogDataListResponse logDataListResponse = new LogDataListResponse(logVOS);

        assertThat(logDataListResponse.getLogData()).isEqualTo(logVOS);
    }

    @Test
    public void MainPageDataListResponseTest() {
        ArrayList<MainPageVO> mainPageVOS = new ArrayList<MainPageVO>();
        mainPageVOS.add(new MainPageVO());
        mainPageVOS.add(new MainPageVO("android", "1970-01-01 09:00:00.000", 0, 1, 2, 3, 4));
        MainPageDataListResponse logDataListResponse = new MainPageDataListResponse(mainPageVOS);

        assertThat(logDataListResponse.getLogData()).isEqualTo(mainPageVOS);
    }

    @Test
    public void SetDataListResponseTest() {
        HashSet<String> stringHashSet = new HashSet<String>();
        stringHashSet.add("android");
        SetDataListResponse logDataListResponse = new SetDataListResponse(stringHashSet);

        assertThat(logDataListResponse.getLogData()).isEqualTo(stringHashSet);
    }

    @Test
    public void mainPageVOTest() {
        MainPageVO mainPageVO = new MainPageVO();
        mainPageVO.setPackageName("android");
        mainPageVO.setRecentCrashTime("1970-01-01 09:00:00.000");
        mainPageVO.setVerb(0);
        mainPageVO.setInfo(1);
        mainPageVO.setDebug(2);
        mainPageVO.setWarning(3);
        mainPageVO.setError(5);

        assertThat(mainPageVO.getPackageName()).isEqualTo(mainPageVO.getPackageName());
        assertThat(mainPageVO.getRecentCrashTime()).isEqualTo(mainPageVO.getRecentCrashTime());
        assertThat(mainPageVO.getVerb()).isEqualTo(mainPageVO.getVerb());
        assertThat(mainPageVO.getInfo()).isEqualTo(mainPageVO.getInfo());
        assertThat(mainPageVO.getDebug()).isEqualTo(mainPageVO.getDebug());
        assertThat(mainPageVO.getWarning()).isEqualTo(mainPageVO.getWarning());
        assertThat(mainPageVO.getError()).isEqualTo(mainPageVO.getError());
    }
}
