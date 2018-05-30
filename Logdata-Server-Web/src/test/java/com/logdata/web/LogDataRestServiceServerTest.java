package com.logdata.web;

import com.logdata.web.controller.LogDataController;
import com.logdata.web.service.RestAPIManager;
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
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@RestClientTest(RestAPIManager.class)
public class LogDataRestServiceServerTest {
    @Autowired
    private MockRestServiceServer server;
    @Autowired
    private LogDataController logDataController;
    @Autowired
    private MockMvc mvc;

    private final String API_BASE_URL = "http://localhost:8081";
    private final String WEB_BASE_URL = "http://localhost:8080";

    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("templates/");
        viewResolver.setSuffix(".html");

        mvc = MockMvcBuilders
                .standaloneSetup(logDataController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void logDataPostTest() throws Exception {
        this.server.expect(
                requestTo(API_BASE_URL + "/api/log-data"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withSuccess(
                                "{\"result\":\"Log Data Transfer Success\"}",
                                MediaType.APPLICATION_JSON_UTF8
                        )
                );

        MockHttpServletResponse response = mvc.perform(
                post("/log-data")
                        .with(
                                user("user")
                                        .password("user")
                                        .roles("USER")
                        )
                        .header("apiKey", "key")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{}")
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        this.server.verify();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo("{\"result\":\"Log Data Transfer Success\"}");
    }

    @Test
    public void logDataLevelListTest() throws Exception {
        this.server.expect(
                requestTo(API_BASE_URL + "/user/find/query?name=user"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "{}",
                        MediaType.APPLICATION_JSON_UTF8
                        )
                );

        this.server.expect(
                requestTo(API_BASE_URL + "/api/log-data/filter/level/query?package-name=android3&level=v"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                "[{}]",
                                MediaType.APPLICATION_JSON_UTF8
                        )
                );

        MockHttpServletResponse response = mvc.perform(
                get("/log-data/filter/level/query")
                        .principal(
                                new Principal() {
                                    @Override
                                    public String getName() {
                                        return "user";
                                    }
                                }
                        )
                        .param("package-name", "android3")
                        .param("level", "v")
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        this.server.verify();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo("[{\"packageName\":null,\"level\":null,\"tag\":null,\"message\":null,\"time\":0,\"memoryInfo\":null}]");
    }

    @Test
    public void logDataTagListTest() throws Exception {
        this.server.expect(
                requestTo(API_BASE_URL + "/user/find/query?name=user"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "{}",
                        MediaType.APPLICATION_JSON_UTF8
                        )
                );

        this.server.expect(
                requestTo(API_BASE_URL + "/api/log-data/filter/tag/query?package-name=android3&tag=%5BMainActivity::onCreate%5D"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                "[{}]",
                                MediaType.APPLICATION_JSON_UTF8
                        )
                );

        MockHttpServletResponse response = mvc.perform(
                get("/log-data/filter/tag/query")
                        .principal(
                                new Principal() {
                                    @Override
                                    public String getName() {
                                        return "user";
                                    }
                                }
                        )
                        .param("package-name", "android3")
                        .param("tag", "[MainActivity::onCreate]")
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        this.server.verify();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo("[{\"packageName\":null,\"level\":null,\"tag\":null,\"message\":null,\"time\":0,\"memoryInfo\":null}]");
    }

//    @Test
//    public void logDataPackageNameListTest() throws Exception {
//        this.server.expect(
//                requestTo(API_BASE_URL + "/user/find/query?name=user"))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withSuccess(
//                        "{}",
//                        MediaType.APPLICATION_JSON_UTF8
//                        )
//                );
//
//        this.server.expect(
//                requestTo(API_BASE_URL + "/api/log-data/filter/package-name/query?package-name=android3"))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(
//                        withSuccess(
//                                "[{}]",
//                                MediaType.APPLICATION_JSON_UTF8
//                        )
//                );
//
//        MockHttpServletResponse response = mvc.perform(
//                get("/log-data/filter/package-name/query?")
//                        .principal(
//                                new Principal() {
//                                    @Override
//                                    public String getName() {
//                                        return "user";
//                                    }
//                                }
//                        )
//                        .param("package-name", "android3")
//        )
//                .andDo(print())
//                .andReturn()
//                .getResponse();
//
//        this.server.verify();
//
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        assertThat(response.getContentAsString()).isEqualTo("[{\"packageName\":null,\"level\":null,\"tag\":null,\"message\":null,\"time\":0,\"memoryInfo\":null}]");
//    }

//    @Test
//    public void getPackageNameTest() throws Exception {
//        this.server.expect(
//                requestTo(API_BASE_URL + "/user/find/query?name=user"))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withSuccess(
//                        "{}",
//                        MediaType.APPLICATION_JSON_UTF8
//                        )
//                );
//
//        this.server.expect(
//                requestTo(API_BASE_URL + "/api/log-data/package-name/set"))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(
//                        withSuccess(
//                                "[\"com.example.android\", \"com.logdata.android.example\"]",
//                                MediaType.APPLICATION_JSON_UTF8
//                        )
//                );
//
//        MockHttpServletResponse response = mvc.perform(
//                get("/log-data/package-name/set")
//                        .principal(
//                                new Principal() {
//                                    @Override
//                                    public String getName() {
//                                        return "user";
//                                    }
//                                }
//                        )
//        )
//                .andDo(print())
//                .andReturn()
//                .getResponse();
//
//        this.server.verify();
//
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        assertThat(response.getContentAsString()).isEqualTo("[\"com.logdata.android.example\",\"com.example.android\"]");
//    }

    @Test
    public void getTagNameTest() throws Exception {
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
                requestTo(API_BASE_URL + "/api/log-data/tag/set/query?package-name=android"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                "[\"[MainActivity::onCreate]\", \"[Practice::method]\"]",
                                MediaType.APPLICATION_JSON_UTF8
                        )
                );

        MockHttpServletResponse response = mvc.perform(
                get("/log-data/tag/set/query?")
                        .principal(
                                new Principal() {
                                    @Override
                                    public String getName() {
                                        return "user";
                                    }
                                }
                        ).param("package-name", "android")
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        this.server.verify();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo("[\"[MainActivity::onCreate]\",\"[Practice::method]\"]");
    }
}