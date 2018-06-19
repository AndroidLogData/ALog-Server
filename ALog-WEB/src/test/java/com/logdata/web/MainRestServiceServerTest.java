package com.logdata.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logdata.web.controller.MainController;
import com.logdata.web.service.RestAPIManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.assertj.core.api.Assertions.assertThat;
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
}