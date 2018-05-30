package com.logdata.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logdata.common.model.LogDataInfoVO;
import com.logdata.web.controller.MainController;
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
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@RestClientTest(RestAPIManager.class)
public class MainRestServiceServerTest {
    @Autowired
    private MockRestServiceServer server;
    @Autowired
    private MainController mainController;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final String API_BASE_URL = "http://localhost:8081";
    private final String WEB_BASE_URL = "http://localhost:8080";
    private String userDataJsonString;

    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("templates/");
        viewResolver.setSuffix(".html");

        mvc = MockMvcBuilders
                .standaloneSetup(mainController)
                .setViewResolvers(viewResolver)
                .build();

        userDataJsonString = "{\"id\":\"5a893a6441f1180c84d51d91\",\"userID\":\"user\",\"password\":\"$2a$10$IWl.imN.n9/4ltXsR43bl.Zx/2TKANCV4Io99UssMFLFwpD29oZky\",\"apiKey\":\"key\",\"roles\":[{\"roleName\":\"USER\",\"rno\":null}]}";
    }

    @Test
    public void indexPageTest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/")
        )
                .andDo(print())
                .andExpect(view().name("index"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getForwardedUrl()).isEqualTo("templates/index.html");
    }

    @Test
    public void mainPageDataListTest() throws Exception {
        LogDataInfoVO[] logDataInfoVO = new LogDataInfoVO[1];
        logDataInfoVO[0] = new LogDataInfoVO(
                "android",
                "2017-06-24 12:34:59:000",
                0,
                1,
                2,
                3,
                4
        );

        String mainPageVOJsonString = objectMapper.writeValueAsString(logDataInfoVO);

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
                ExpectedCount.manyTimes(),
                requestTo(API_BASE_URL + "/main/main"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                mainPageVOJsonString,
                                MediaType.APPLICATION_JSON_UTF8
                        )
                );

        MockHttpServletResponse response = mvc.perform(
                get("/main")
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
                .andReturn()
                .getResponse();

        this.server.verify();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo("[{\"packageName\":\"android\",\"recentCrashTime\":\"2017-06-24 12:34:59:000\",\"verb\":0,\"info\":1,\"debug\":2,\"warning\":3,\"error\":4}]");
    }

    @Test
    public void mainPageDataListUserNullTest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/main")
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}