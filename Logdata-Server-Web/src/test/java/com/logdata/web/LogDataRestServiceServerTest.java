package com.logdata.web;

import com.logdata.common.model.LogVO;
import com.logdata.common.model.UserVO;
import com.logdata.common.repository.UserDataRepository;
import com.logdata.web.controller.LogDataController;
import com.logdata.web.service.RestAPIUtility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.security.Principal;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@RestClientTest(RestAPIUtility.class)
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
                requestTo(API_BASE_URL + "/api/logdatasave"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withSuccess(
                                "{\"result\":\"Log Data Transfer Success\"}",
                                MediaType.APPLICATION_JSON_UTF8
                        )
                );

        MockHttpServletResponse response = mvc.perform(
                post("/logdata")
                        .with(
                                user("user")
                                        .password("user")
                                        .roles("USER")
                        )
                        .header("secretKey", "key")
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
                requestTo(API_BASE_URL + "/api/logdatalevelfilter/query?packagename=android3&level=v"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                "[{}]",
                                MediaType.APPLICATION_JSON_UTF8
                        )
                );

        MockHttpServletResponse response = mvc.perform(
                get("/logdatalevelfilter/query")
                        .principal(
                                new Principal() {
                                    @Override
                                    public String getName() {
                                        return "user";
                                    }
                                }
                        )
                        .param("packagename", "android3")
                        .param("level", "v")
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        this.server.verify();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo("{\"logData\":[{\"id\":null,\"packageName\":null,\"level\":null,\"tag\":null,\"message\":null,\"time\":0,\"stringTime\":null,\"memoryInfo\":null,\"apiKey\":null}]}");
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
                requestTo(API_BASE_URL + "/api/logdatatagfilter/query?packagename=android3&tag=%5BMainActivity::onCreate%5D"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                "[{}]",
                                MediaType.APPLICATION_JSON_UTF8
                        )
                );

        MockHttpServletResponse response = mvc.perform(
                get("/logdatatagfilter/query")
                        .principal(
                                new Principal() {
                                    @Override
                                    public String getName() {
                                        return "user";
                                    }
                                }
                        )
                        .param("packagename", "android3")
                        .param("tag", "[MainActivity::onCreate]")
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        this.server.verify();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo("{\"logData\":[{\"id\":null,\"packageName\":null,\"level\":null,\"tag\":null,\"message\":null,\"time\":0,\"stringTime\":null,\"memoryInfo\":null,\"apiKey\":null}]}");
    }
}
