package com.logdata.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LogDataMockMVCTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void logDataGetTest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/log-data")
                        .with(
                                user("user")
                                        .password("user")
                                        .roles("USER")
                        )
        )
                .andDo(print())
                .andExpect(view().name("logdata"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
    }

    @Test
    public void logDataPackageNameViewTest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/log-data/filter/package-name/query?")
                        .with(
                                user("user")
                                        .password("user")
                                        .roles("USER")
                        )
        )
                .andDo(print())
                .andExpect(view().name("index"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
    }

    @Test
    public void logDataPackageNameViewFailedTest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/log-data/filter/package-name/query?")
//                        .with(
//                                user("user")
//                                        .password("user")
//                                        .roles("USER")
//                        )
        )
                .andDo(print())
                .andExpect(view().name("login"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
    }
}
