package com.logdata.backend;

import com.logdata.backend.model.UserVO;
import com.logdata.backend.repository.UserDataRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginController {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserDataRepository userDataRepository;

    @Test
    public void loginPageGetTest() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/login")
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

    @Test
    public void registrationPageGetTest() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/registration")
//                        .with(
//                                user("user")
//                                        .password("user")
//                                        .roles("USER")
//                        )
        ).andDo(print())
                .andExpect(view().name("registration"))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
    }

    @Test
    public void registrationPostTest() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                post("/registration")
                        .param("username", "user")
                        .param("password", "user")
                        .with(csrf())
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

    @Test
    public void registrationPostSameIDTest() throws Exception {
        when(userDataRepository.findByUserID("user")).thenReturn(new UserVO("user", "user"));
        // when
        MockHttpServletResponse response = mvc.perform(
                post("/registration")
                        .param("username", "user")
                        .param("password", "user")
                        .with(csrf())
//                        .with(
//                                user("user")
//                                        .password("user")
//                                        .roles("USER")
//                        )
        ).andDo(print())
                .andExpect(model().attribute("sameID", true))
                .andExpect(view().name("registration"))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
    }
}
