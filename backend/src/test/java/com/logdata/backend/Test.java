package com.logdata.backend;

import com.logdata.backend.controller.LogDataController;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration
@WebMvcTest(LogDataController.class)
public class Test {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @MockBean
    private LogDataController logDataController;
    private String baseUrl;
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
        baseUrl = "http://localhost:" +  String.valueOf(8080);
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @org.junit.Test
    public void logDataUserDataNullTest() {
        try {
            mvc.perform(get(baseUrl + "/logdata"))
                    .andDo(print())
                    .andExpect(status().isUnauthorized());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void logDataPageGetTest() {
        try {
            mvc.perform(get(baseUrl + "/logdata").with(user("user").password("user").roles("USER")))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void logDataPostButNotAPIKeyTest() {
        try {
            mvc.perform(post(baseUrl + "/logdata").with(user("user").password("user").roles("USER")))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void logDataPostTest() {
        try {
            mvc.perform(
                    post(baseUrl + "/logdata")
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
                    .andExpect(status().isOk()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void logDataTransferTest() {
        try {
            mvc.perform(get(baseUrl + "/logdata").with(user("user").password("user").roles("USER")))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
