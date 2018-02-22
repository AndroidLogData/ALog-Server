package com.logdata.web;

import com.logdata.api.service.LogDataService;
import com.logdata.api.service.UserService;
import com.logdata.common.model.LogVO;
import com.logdata.common.model.UserVO;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LogDataControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private LogDataService logDataService;
    @MockBean
    private UserService userService;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<LogVO> logVOJacksonTester;

    @Test
    public void logDataInputTest() {
        long currentTime = System.currentTimeMillis();
        LogVO log = new LogVO("1", "android", "i", "[MainActivity::onCreate]", "Hello, World!", new DateTime(currentTime), null ,"apiKey");

        given(logDataService.findOne("1")).willReturn(log);

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
    public void logDataListTest() throws Exception {
        UserVO user = new UserVO("user", "user");
        when(userService.findByUserID("user")).thenReturn(user);
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/logdatalist")
                        .with(
                                user("user")
                                        .password("user")
                                        .roles("USER")
                        )
        ).andDo(print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8.toString());
        assertThat(response.getContentAsString()).isEqualTo("{\"logData\":[]}");
    }

    @Test
    public void logDataUserDataNullTest() {
        try {
            MockHttpServletResponse response = mvc.perform(get("/logdata").header("secretKey", "apiKey"))
                    .andDo(print())
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
            assertThat(response.getRedirectedUrl()).isEqualTo("http://localhost/login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logDataPageGetTest() {
        try {
            MockHttpServletResponse response = mvc.perform(get("/logdata").with(user("user").password("user").roles("USER")))
                    .andDo(print()).andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logDataPostButNotAPIKeyTest() {
        try {
            MockHttpServletResponse response = mvc.perform(post("/logdata").with(user("user").password("user").roles("USER")))
                    .andDo(print())
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.getErrorMessage()).isEqualTo("Missing request header 'secretKey' for method parameter of type String");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logDataPostTest() {
        try {
            MockHttpServletResponse response = mvc.perform(
                    post("/logdata")
                            .header("secretKey", "apiKey")
                            .with(
                                    user("user")
                                            .password("user")
                                            .roles("USER")
                            )
                            .contentType("application/json;charset=utf-8")
                            .content("{\"packageName\" : \"android3\", \"message\" : \"MainActivity onCreate\", \"tag\" : \"[MainActivity::onCreate]\", \"time\" : 846516546863, \"level\" : \"v\", \"memoryInfo\" : { \"memoryPercentage\" : 31.041206627151695, \"dalvikPss\" : 2660, \"lowMemory\" : false, \"nativePss\" : 1889, \"totalPss\" : 17418, \"availMemory\" : 1093652480, \"threshold\" : 150994944, \"totalMemory\" : 1585950720, \"otherPss\" : 22869}}")
            )
                    .andDo(print())
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8.toString());
            assertThat(response.getContentAsString()).isEqualTo("{\"result\":\"Log Data Transfer Success\"}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logDataLevelFilterTest() {
        try {
            given(this.userService.findByUserID("user")).willReturn(new UserVO("user", "user"));
            MockHttpServletResponse response = mvc.perform(
                    get("/logdatalevelfilter/query?")
                            .param("packagename", "android")
                            .param("level", "i")
                            .with(
                                    user("user")
                                            .password("user")
                                            .roles("USER")
                            )
            )
                    .andDo(print())
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8.toString());
            assertThat(response.getContentAsString()).isEqualTo("{\"logData\":[]}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logDataTagFilterTest() {
        try {
            given(this.userService.findByUserID("user")).willReturn(new UserVO("user", "user"));
            MockHttpServletResponse response = mvc.perform(
                    get("/logdatatagfilter/query?")
                            .param("packagename", "android")
                            .param("tag", "[MainActivity::onCreate]")
                            .with(
                                    user("user")
                                            .password("user")
                                            .roles("USER")
                            )
            )
                    .andDo(print())
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8.toString());
            assertThat(response.getContentAsString()).isEqualTo("{\"logData\":[]}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logDataPackageNameFilterTest() {
        try {
            given(this.userService.findByUserID("user")).willReturn(new UserVO("user", "user"));
            MockHttpServletResponse response = mvc.perform(
                    get("/logdatapackagenamefilter/query?")
                            .param("packagename", "android")
                            .with(
                                    user("user")
                                            .password("user")
                                            .roles("USER")
                            )
            )
                    .andDo(print())
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8.toString());
            assertThat(response.getContentAsString()).isEqualTo("{\"logData\":[]}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tagListTest() {
        try {
            given(this.userService.findByUserID("user")).willReturn(new UserVO("user", "user"));
            MockHttpServletResponse response = mvc.perform(
                    get("/tagdatalist")
                            .with(
                                    user("user")
                                            .password("user")
                                            .roles("USER")
                            )
            )
                    .andDo(print())
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8.toString());
            assertThat(response.getContentAsString()).isEqualTo("{\"logData\":[]}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void packageNameListTest() {
        try {
            given(this.userService.findByUserID("user")).willReturn(new UserVO("user", "user"));
            MockHttpServletResponse response = mvc.perform(
                    get("/packagenamedatalist")
                            .with(
                                    user("user")
                                            .password("user")
                                            .roles("USER")
                            )
            )
                    .andDo(print())
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8.toString());
            assertThat(response.getContentAsString()).isEqualTo("{\"logData\":[]}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logDataPackageNameViewTest() {
        try {
            MockHttpServletResponse response = mvc.perform(
                    get("/packagenamefilter/query?")
                            .param("packagename", "android")
                            .with(
                                    user("user")
                                            .password("user")
                                            .roles("USER")
                            )
            )
                    .andDo(print()).andExpect(view().name("index"))
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logDataPackageNameViewFailedTest() {
        try {
            MockHttpServletResponse response = mvc.perform(
                    get("/packagenamefilter/query?")
                            .param("packagename", "android")
//                            .with(
//                                    user("user")
//                                            .password("user")
//                                            .roles("USER")
//                            )
            )
                    .andDo(print()).andExpect(view().name("login"))
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
