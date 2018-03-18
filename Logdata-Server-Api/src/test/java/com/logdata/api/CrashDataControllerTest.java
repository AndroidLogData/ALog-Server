package com.logdata.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logdata.api.sevice.CrashDataService;
import com.logdata.common.model.CrashVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CrashDataControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private CrashDataService crashDataService;

    private ArrayList<CrashVO> list;

    @Autowired
    private ObjectMapper objectMapper;
    private String crashDataJsonString;

    private CrashVO newCrashData;
    private HashMap<String, Object> display;
    private HashMap<String, Object> displayContent;
    private HashMap<String, Object> deviceFeatures;
    private HashMap<String, Object> build;
    private ArrayList<Integer> sizeList;

    @Before
    public void setUp() throws Exception {
        list = new ArrayList<>();
        list.add(new CrashVO("android", 1L, "5.0", "1.0", "1.0", 1L, "samsung", "logcat", "deviceID", new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), "key"));

        sizeList = new ArrayList<Integer>();
        sizeList.add(1080);
        sizeList.add(1920);

        display = new HashMap<String, Object>();
        displayContent = new HashMap<String, Object>();
        displayContent.put("realSize", sizeList);
        displayContent.put("rotation", "ROTATION_0");
        display.put("0", displayContent);

        deviceFeatures = new HashMap<String, Object>();
        deviceFeatures.put("android.hardware.wifi.direct", true);

        build = new HashMap<String, Object>();
        build.put("BOOTLOADER", "N900SKSU0GPI1");
        build.put("BRAND", "samsung");
        build.put("CPU_ABI", "armeabi-v7a");
        build.put("CPU_ABI2", "armeabi");
        build.put("DISPLAY", "LRX21V.N900SKSU0GPI1");
        build.put("DEVICE", "hlteskt");
        build.put("MODEL", "SM-N900S");
        build.put("BOARD", "MSM8974");

        newCrashData = new CrashVO(
                "android",
                1L,
                "5.0",
                "1.0",
                "1.0",
                1L,
                "samsung",
                "logcat",
                "deviceID",
                display,
                null,
                deviceFeatures,
                build,
                "key");

        crashDataJsonString = objectMapper.writeValueAsString(newCrashData);
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void crashDataSaveTest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                post("/api/crash")
                        .header("secretKey", "key")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(crashDataJsonString)
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo("{\"result\":\"Crash Data Transfer Success\"}");
    }

    @Test
    public void crashDataTimeViewFailedTest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/api/crash/filter/time/query")
                        .header("secretKey", "key")
                        .param("time", "1")
                        .param("packageName", "android")
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void crashDataTimeViewTest() throws Exception {
        when(crashDataService.findCrashDataByTimeAndApiKeyAndPackageName(1L, "key", "android")).thenReturn(newCrashData);
        MockHttpServletResponse response = mvc.perform(
                get("/api/crash/filter/time/query")
                        .header("secretKey", "key")
                        .param("time", "1")
                        .param("packageName", "android")
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo(crashDataJsonString);
    }

    @Test
    public void crashPackageNamePageFailedTest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/api/crash/filter/packagename/query")
                        .header("secretKey", "key")
                        .param("packageName", "android")
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void crashPackageNamePageTest() throws Exception {
        when(crashDataService.findCrashDataByPackageNameAndApiKeyOrderByTimeDesc("android", "key")).thenReturn(newCrashData);
        MockHttpServletResponse response = mvc.perform(
                get("/api/crash/filter/packagename/query")
                        .header("secretKey", "key")
                        .param("packageName", "android")
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo(crashDataJsonString);
    }

    @Test
    public void getPackageNamePageTest() throws Exception {
        when(crashDataService.findByApiKey("key")).thenReturn(list);
        MockHttpServletResponse response = mvc.perform(
                get("/api/crash/packagename/set")
                        .header("secretKey", "key")
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo("[\"android\"]");
    }

    @Test
    public void getCrashTimeTest() throws Exception {
        when(crashDataService.findByApiKeyAndPackageNameOrderByTimeAsc("key", "android")).thenReturn(list);
        MockHttpServletResponse response = mvc.perform(
                get("/api/crash/packagename/time/query")
                        .header("secretKey", "key")
                        .param("packageName", "android")
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo("[{\"time\":1,\"packageName\":\"android\",\"stringTime\":\"1970-01-01 00:00:00.001\"}]");
    }
}