package com.logdata.backend;

import com.logdata.backend.repository.CrashDataRepository;
import com.logdata.backend.repository.UserDataRepository;
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
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelpControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserDataRepository userDataRepository;
    @MockBean
    private CrashDataRepository crashDataRepository;

    @org.junit.Test
    public void helpPageGetTest() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/help")
                        .with(
                                user("user")
                                        .password("user")
                                        .roles("USER")
                        )
        ).andDo(print())
                .andExpect(view().name("help"))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
    }

    @org.junit.Test
    public void myPageGetTest() throws Exception {
        UserVO user = new UserVO("user", "user");
        ArrayList<CrashVO> crashVOs = new ArrayList<CrashVO>();

        crashVOs.add(new CrashVO());

        when(userDataRepository.findByUserID("user")).thenReturn(user);
        when(crashDataRepository.findAllByApiKeyOrderByTimeDesc("apiKey")).thenReturn(crashVOs);
        HashMap<String, Integer> map = new HashMap<String, Integer>();
//        map.put("NullPointerException", 1);
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/mypage")
                        .with(
                                user("user")
                                        .password("user")
                                        .roles("USER")
                        )
        ).andDo(print())
                .andExpect(view().name("mypage"))
                .andExpect(model().attribute("crashList", map))
                .andExpect(model().attribute("apikey", user.getApiKey()))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
    }

    @org.junit.Test
    public void myPageGetFailedTest() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/mypage")
//                        .with(
//                                user("user")
//                                        .password("user")
//                                        .roles("USER")
//                        )
        ).andDo(print())
                .andExpect(view().name("login"))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
    }
}
