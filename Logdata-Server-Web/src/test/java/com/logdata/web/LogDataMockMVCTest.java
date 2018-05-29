//package com.logdata.web;
//
//import com.logdata.web.controller.LogDataController;
//import com.logdata.web.service.RestAPIUtility;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.client.MockRestServiceServer;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
//import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@RestClientTest(RestAPIUtility.class)
//public class LogDataMockMVCTest {
//    @Autowired
//    private MockMvc mvc;
//    @Autowired
//    private MockRestServiceServer server;
//    @Autowired
//    private LogDataController logDataController;
//    private final String API_BASE_URL = "http://localhost:8081";
//
//    @Before
//    public void setup() {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("templates/");
//        viewResolver.setSuffix(".html");
//
//        mvc = MockMvcBuilders
//                .standaloneSetup(logDataController)
//                .setViewResolvers(viewResolver)
//                .build();
//    }
//
//    @Test
//    public void logDataGetTest() throws Exception {
//        MockHttpServletResponse response = mvc.perform(
//                get("/log-data")
//                        .with(
//                                user("user")
//                                        .password("user")
//                                        .roles("USER")
//                        )
//        )
//                .andDo(print())
//                .andExpect(view().name("logdata"))
//                .andReturn()
//                .getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
//    }
//
//    @Test
//    public void logDataPackageNameViewTest() throws Exception {
//        this.server.expect(
//                requestTo(API_BASE_URL + "/user/find/query?name=user"))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withSuccess(
//                        "{}",
//                        MediaType.APPLICATION_JSON_UTF8
//                        )
//                );
//
//        MockHttpServletResponse response = mvc.perform(
//                get("/log-data/filter/package-name/query?")
//                        .with(
//                                user("user")
//                                        .password("user")
//                                        .roles("USER")
//                        )
//        )
//                .andDo(print())
//                .andExpect(view().name("index"))
//                .andReturn()
//                .getResponse();
//
//        this.server.verify();
//
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
//    }
//
//    @Test
//    public void logDataPackageNameViewFailedTest() throws Exception {
//        MockHttpServletResponse response = mvc.perform(
//                get("/log-data/filter/package-name/query?")
////                        .with(
////                                user("user")
////                                        .password("user")
////                                        .roles("USER")
////                        )
//        )
//                .andDo(print())
//                .andExpect(view().name("login"))
//                .andReturn()
//                .getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
//    }
//}
