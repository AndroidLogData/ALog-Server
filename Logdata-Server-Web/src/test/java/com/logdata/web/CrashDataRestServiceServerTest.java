package com.logdata.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logdata.common.model.CrashTimeVO;
import com.logdata.common.model.CrashVO;
import com.logdata.web.controller.CrashDataController;
import com.logdata.web.service.RestAPIUtility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
@RestClientTest(RestAPIUtility.class)
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
                build,
                "key");

        crashDataJsonString = objectMapper.writeValueAsString(newCrashData);

        userDataJsonString = "{\"id\":\"5a893a6441f1180c84d51d91\",\"userID\":\"user\",\"password\":\"$2a$10$IWl.imN.n9/4ltXsR43bl.Zx/2TKANCV4Io99UssMFLFwpD29oZky\",\"apiKey\":\"key\",\"roles\":[{\"roleName\":\"USER\",\"rno\":null}]}";
    }

    @Test
    public void crashPageTest() throws Exception {
        this.server.expect(
                requestTo(API_BASE_URL + "/user/find/query?name=user"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                "{}",
                                MediaType.APPLICATION_JSON_UTF8
                        )
                );

        this.server.expect(
                requestTo(API_BASE_URL + "/api/crash/packagename/set"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                "[\"com.example.android\", \"com.example.practice\"]",
                                MediaType.APPLICATION_JSON_UTF8
                        )
                );

        MockHttpServletResponse response = mvc.perform(
                get("/crash")
                        .principal(
                                new Principal() {
                                    @Override
                                    public String getName() {
                                        return "user";
                                    }
                                }
                        )
        )
                .andDo(print())
                .andExpect(view().name("crash"))
                .andExpect(model().attribute("noData", true))
                .andExpect(model().attribute("sideMenuItems", sideMenuItemsList))
                .andReturn()
                .getResponse();

        this.server.verify();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getForwardedUrl()).isEqualTo("templates/crash.html");
    }

    @Test
    public void crashPageUserNullTest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/crash")
        )
                .andDo(print())
                .andExpect(view().name("login"))
                .andReturn()
                .getResponse();

        this.server.verify();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getForwardedUrl()).isEqualTo("templates/login.html");
    }

    @Test
    public void crashDataTimeViewTest() throws Exception {
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
                requestTo(API_BASE_URL + "/api/crash/filter/time/query?time=1&packageName=com.example.android"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                crashDataJsonString,
                                MediaType.APPLICATION_JSON_UTF8
                        )
                );

        this.server.expect(
                requestTo(API_BASE_URL + "/api/crash/packagename/time/query?packageName=com.example.android"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                crashTimeDataJsonString,
                                MediaType.APPLICATION_JSON_UTF8
                        )
                );

        this.server.expect(
                requestTo(API_BASE_URL + "/api/crash/packagename/set"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                "[\"com.example.android\", \"com.example.practice\"]",
                                MediaType.APPLICATION_JSON_UTF8
                        )
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
                        .param("packageName", "com.example.android")
        )
                .andDo(print())
                .andExpect(view().name("crash"))
                .andExpect(model().attribute("noData", false))
//                .andExpect(model().attribute("crash", newCrashData))
                .andExpect(model().attribute("logcat", ""))
//                .andExpect(model().attribute("time", "1970-01-01 09:00:00.001"))
                .andExpect(model().attribute("realSize", displayContent.get("realSize")))
                .andExpect(model().attribute("rotation", "ROTATION_0"))
                .andExpect(model().attribute("bootLoader", "N900SKSU0GPI1"))
                .andExpect(model().attribute("CPU_ABI", "armeabi-v7a"))
                .andExpect(model().attribute("CPU_ABI2", "armeabi"))
                .andExpect(model().attribute("buildDisplay", "LRX21V.N900SKSU0GPI1"))
                .andExpect(model().attribute("TWRP", "hlteskt"))
                .andExpect(model().attribute("deviceFeatures", deviceFeatures))
                .andExpect(model().attribute("model", "SM-N900S"))
                .andExpect(model().attribute("board", "MSM8974"))
//                .andExpect(model().attribute("timeData", crashTimeDataJsonString))
                .andExpect(model().attribute("sideMenuItems", sideMenuItemsList))
                .andReturn()
                .getResponse();

        this.server.verify();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getForwardedUrl()).isEqualTo("templates/crash.html");
    }

    @Test
    public void crashDataTimeViewUserNullTest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/crash/filter/time/query")
                        .param("time", String.valueOf(1L))
                        .param("packageName", "com.example.android")
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
                requestTo(API_BASE_URL + "/api/crash/filter/time/query?time=1&packageName=com.example.android"))
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
                        .param("packageName", "com.example.android")
                        .content("")
        )
                .andDo(print())
                .andExpect(view().name("nodata"))
                .andReturn()
                .getResponse();

        this.server.verify();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getForwardedUrl()).isEqualTo("templates/nodata.html");
    }

    @Test
    public void crashPackageNamePageTest() throws Exception {
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
                requestTo(API_BASE_URL + "/api/crash/filter/packagename/query?packageName=android3"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                crashDataJsonString,
                                MediaType.APPLICATION_JSON_UTF8
                        )
                );

        this.server.expect(
                requestTo(API_BASE_URL + "/api/crash/packagename/time/query?packageName=android3"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                crashTimeDataJsonString,
                                MediaType.APPLICATION_JSON_UTF8
                        )
                );

        this.server.expect(
                requestTo(API_BASE_URL + "/api/crash/packagename/set"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                "[\"com.example.android\", \"com.example.practice\"]",
                                MediaType.APPLICATION_JSON_UTF8
                        )
                );

        MockHttpServletResponse response = mvc.perform(
                get("/crash/filter/packagename/query")
                        .principal(
                                new Principal() {
                                    @Override
                                    public String getName() {
                                        return "user";
                                    }
                                }
                        )
                        .param("packageName", "android3")
        )
                .andDo(print())
                .andExpect(view().name("crash"))
                .andExpect(model().attribute("noData", false))
//                .andExpect(model().attribute("crash", newCrashData))
                .andExpect(model().attribute("logcat", ""))
//                .andExpect(model().attribute("time", "1970-01-01 09:00:00.001"))
                .andExpect(model().attribute("realSize", displayContent.get("realSize")))
                .andExpect(model().attribute("rotation", "ROTATION_0"))
                .andExpect(model().attribute("bootLoader", "N900SKSU0GPI1"))
                .andExpect(model().attribute("CPU_ABI", "armeabi-v7a"))
                .andExpect(model().attribute("CPU_ABI2", "armeabi"))
                .andExpect(model().attribute("buildDisplay", "LRX21V.N900SKSU0GPI1"))
                .andExpect(model().attribute("TWRP", "hlteskt"))
                .andExpect(model().attribute("deviceFeatures", deviceFeatures))
                .andExpect(model().attribute("model", "SM-N900S"))
                .andExpect(model().attribute("board", "MSM8974"))
//                .andExpect(model().attribute("timeData", crashTimeDataJsonString))
                .andExpect(model().attribute("sideMenuItems", sideMenuItemsList))
                .andReturn()
                .getResponse();

        this.server.verify();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getForwardedUrl()).isEqualTo("templates/crash.html");
    }

    @Test
    public void crashPackageNamePageUserNullTest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/crash/filter/packagename/query")
                        .param("packageName", "com.example.android")
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
                requestTo(API_BASE_URL + "/api/crash/filter/packagename/query?packageName=com.example.android"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withNoContent()
                );

        MockHttpServletResponse response = mvc.perform(
                get("/crash/filter/packagename/query")
                        .principal(
                                new Principal() {
                                    @Override
                                    public String getName() {
                                        return "user";
                                    }
                                }
                        )
                        .param("packageName", "com.example.android")
                        .content("")
        )
                .andDo(print())
                .andExpect(view().name("crash"))
                .andExpect(model().attribute("noData", true))
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
                        .header("secretKey", "key")
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