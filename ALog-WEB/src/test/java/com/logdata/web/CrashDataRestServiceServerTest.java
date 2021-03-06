package com.logdata.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logdata.common.model.CrashTimeVO;
import com.logdata.common.model.CrashVO;
import com.logdata.web.controller.CrashDataController;
import com.logdata.web.service.RestAPIManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withNoContent;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@RestClientTest(RestAPIManager.class)
public class CrashDataRestServiceServerTest {
    @Autowired
    private MockRestServiceServer server;
    @Autowired
    private CrashDataController crashDataController;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final String API_BASE_URL = "http://localhost:8081";
    private final String WEB_BASE_URL = "http://localhost:8080";
    private String crashDataJsonString;
    private String userDataJsonString;
    private String crashTimeDataJsonString;

    private CrashVO newCrashData;
    private HashMap<String, Object> display;
    private HashMap<String, Object> displayContent;
    private HashMap<String, Object> deviceFeatures;
    private HashMap<String, Object> build;
    private ArrayList<Integer> sizeList;
    private ArrayList<String> sideMenuItemsList;
    private CrashTimeVO[] crashTimeVO;

    @Before
    public void setup() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("templates/");
        viewResolver.setSuffix(".html");

        mvc = MockMvcBuilders
                .standaloneSetup(crashDataController)
                .setViewResolvers(viewResolver)
                .build();

        sideMenuItemsList = new ArrayList<String>();
        sideMenuItemsList.add("com.example.android");
        sideMenuItemsList.add("com.example.practice");

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

        crashTimeVO = new CrashTimeVO[1];
        crashTimeVO[0] = new CrashTimeVO(1L, "android", "2021-07-19 00:09:54.631");
        crashTimeDataJsonString = objectMapper.writeValueAsString(crashTimeVO);

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
                build);

        crashDataJsonString = objectMapper.writeValueAsString(newCrashData);

        userDataJsonString = "{\"id\":\"5a893a6441f1180c84d51d91\",\"userID\":\"user\",\"password\":\"$2a$10$IWl.imN.n9/4ltXsR43bl.Zx/2TKANCV4Io99UssMFLFwpD29oZky\",\"apiKey\":\"key\",\"roles\":[{\"roleName\":\"USER\",\"rno\":null}]}";
    }

    @Test
    public void crashDataTimeViewUserNullTest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/crash/filter/time/query")
                        .param("time", String.valueOf(1L))
                        .param("package-name", "com.example.android")
        )
                .andDo(print())
                .andExpect(view().name("login"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getForwardedUrl()).isEqualTo("templates/login.html");
    }

    @Test
    public void crashDataTimeViewCrashVONullTest() throws Exception {
        this.server.expect(
                ExpectedCount.manyTimes(),
                requestTo(API_BASE_URL + "/user/find/query?name=user"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                userDataJsonString,
                                MediaType.APPLICATION_JSON_UTF8
                        )
                );

        this.server.expect(
                requestTo(API_BASE_URL + "/api/crash/filter/time/query?time=1&package-name=com.example.android"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withNoContent()
                );

        this.server.expect(
                requestTo(API_BASE_URL + "/api/log-data/package-name/set"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withNoContent()
                );

        MockHttpServletResponse response = mvc.perform(
                get("/crash/filter/time/query")
                        .principal(
                                new Principal() {
                                    @Override
                                    public String getName() {
                                        return "user";
                                    }
                                }
                        )
                        .param("time", String.valueOf(1L))
                        .param("package-name", "com.example.android")
                        .content("")
        )
                .andDo(print())
                .andExpect(view().name("crash"))
                .andReturn()
                .getResponse();

        this.server.verify();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getForwardedUrl()).isEqualTo("templates/crash.html");
    }

    @Test
    public void crashPackageNamePageUserNullTest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/crash/filter/package-name/query")
                        .param("package-name", "com.example.android")
        )
                .andDo(print())
                .andExpect(view().name("login"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getForwardedUrl()).isEqualTo("templates/login.html");
    }

    @Test
    public void crashPackageNamePageCrashVONullTest() throws Exception {
        this.server.expect(
                ExpectedCount.manyTimes(),
                requestTo(API_BASE_URL + "/user/find/query?name=user"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                userDataJsonString,
                                MediaType.APPLICATION_JSON_UTF8
                        )
                );

        this.server.expect(
                requestTo(API_BASE_URL + "/api/crash/filter/package-name/query?package-name=com.example.android"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withNoContent()
                );

        this.server.expect(
                requestTo(API_BASE_URL + "/api/log-data/package-name/set"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withNoContent()
                );

        MockHttpServletResponse response = mvc.perform(
                get("/crash/filter/package-name/query")
                        .principal(
                                new Principal() {
                                    @Override
                                    public String getName() {
                                        return "user";
                                    }
                                }
                        )
                        .param("package-name", "com.example.android")
                        .content("")
        )
                .andDo(print())
                .andExpect(view().name("crash"))
//                .andExpect(model().attribute("packageName", null))
//                .andExpect(model().attribute("packageNameList", null))
                .andReturn()
                .getResponse();

        this.server.verify();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getForwardedUrl()).isEqualTo("templates/crash.html");
    }

    @Test
    public void crashDataSaveTest() throws Exception {
        this.server.expect(
                requestTo(API_BASE_URL + "/api/crash"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withSuccess(
                                crashDataJsonString,
                                MediaType.APPLICATION_JSON_UTF8
                        )
                );

        MockHttpServletResponse response = mvc.perform(
                post("/crash")
                        .header("apiKey", "key")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{}")
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo(crashDataJsonString);
    }
}