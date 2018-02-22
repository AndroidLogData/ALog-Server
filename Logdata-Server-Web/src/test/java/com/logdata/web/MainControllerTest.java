package com.logdata.web;

import com.logdata.api.service.LogDataService;
import com.logdata.api.service.UserService;
import com.logdata.common.model.LogVO;
import com.logdata.common.model.UserVO;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private LogDataService logDataService;
    @MockBean
    private UserService userService;

    @Test
    public void indexPageGetTest() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/")
//                        .with(
//                                user("user")
//                                        .password("user")
//                                        .roles("USER")
//                        )
        ).andDo(print())
                .andExpect(view().name("index"))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
    }

    @Test
    public void mainPageGetTest() throws Exception {
        List<LogVO> logVOs = new ArrayList<>();
        UserVO user = new UserVO("user", "user");
        when(userService.findByUserID("user")).thenReturn(user);
        when(logDataService.findByApiKeyAndPackageNameAndLevel(user.getApiKey(), "android", "v")).thenReturn(logVOs);
        when(logDataService.findByApiKeyAndPackageNameAndLevel(user.getApiKey(), "android", "i")).thenReturn(logVOs);
        when(logDataService.findByApiKeyAndPackageNameAndLevel(user.getApiKey(), "android", "d")).thenReturn(logVOs);
        when(logDataService.findByApiKeyAndPackageNameAndLevel(user.getApiKey(), "android", "w")).thenReturn(logVOs);
        when(logDataService.findByApiKeyAndPackageNameAndLevel(user.getApiKey(), "android", "e")).thenReturn(logVOs);
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/main")
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
}
