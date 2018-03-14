package com.logdata.api;

import com.logdata.api.sevice.LogDataService;
import com.logdata.common.model.LogVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiApplicationTests {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private LogDataService logDataService;

    private ArrayList<LogVO> list;

    @Before
    public void setUp() {
        list = new ArrayList<>();
        list.add(new LogVO("android3", "v", "MainActivity", "Hello", 1L, null, "key"));
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void logDataSave() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                post("/api/logdata")
                        .header("secretKey", "key")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{}")
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo("{\"result\":\"Log Data Transfer Success\"}");
    }

    @Test
    public void logDataSaveNotApiKey() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                post("/api/logdata")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{}")
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getErrorMessage()).isEqualTo("Missing request header 'secretKey' for method parameter of type String");
    }

    @Test
    public void logDataLevelFilterTest() throws Exception {
//        given(logDataService.findByApiKeyAndPackageNameAndLevel("key", "android3", "v")).willReturn(list);
        when(logDataService.findByApiKeyAndPackageNameAndLevel("key", "android3", "v")).thenReturn(list);
        MockHttpServletResponse response = mvc.perform(
                get("/api/logdata/filter/level/query")
                        .header("secretKey", "key")
                        .param("packagename", "android3")
                        .param("level", "v")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    public void logDataTagFilterTest() throws Exception {
//        given(logDataService.findByApiKeyAndPackageNameAndLevel("key", "android3", "v")).willReturn(list);
        when(logDataService.findByApiKeyAndPackageNameAndTag("key", "android3", "[MainActivity::onCreate]", new Sort(Sort.Direction.ASC, "time"))).thenReturn(list);
        MockHttpServletResponse response = mvc.perform(
                get("/api/logdata/filter/tag/query")
                        .header("secretKey", "key")
                        .param("packagename", "android3")
                        .param("tag", "[MainActivity::onCreate]")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    public void getTagTest() throws Exception {
        when(logDataService.findByApiKey("key")).thenReturn(list);
        MockHttpServletResponse response = mvc.perform(
                get("/api/logdata/tag/set")
                        .header("secretKey", "key")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo("[\"MainActivity\"]");
    }

    @Test
    public void getPackageNameTest() throws Exception {
        when(logDataService.findByApiKey("key")).thenReturn(list);
        MockHttpServletResponse response = mvc.perform(
                get("/api/logdata/packagename/set")
                        .header("secretKey", "key")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo("[\"android3\"]");
    }

    @Test
    public void logDataPackageNameListTest() throws Exception {
        when(logDataService.findByApiKeyAndPackageName("key", "android3")).thenReturn(list);
        MockHttpServletResponse response = mvc.perform(
                get("/api/logdata/filter/packagename/query")
                        .header("secretKey", "key")
                        .param("packagename", "android3")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    public void allLogDataTest() throws Exception {
        when(logDataService.findAll()).thenReturn(list);
        MockHttpServletResponse response = mvc.perform(
                get("/api/logdata/list")
                        .header("secretKey", "key")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo("[{\"packageName\":\"android3\",\"level\":\"v\",\"tag\":\"MainActivity\",\"message\":\"Hello\",\"time\":1,\"stringTime\":null,\"memoryInfo\":null,\"apiKey\":\"key\"}]");
    }
}