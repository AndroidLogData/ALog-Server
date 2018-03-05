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

    @Test
    public void contextLoads() {
    }

    @Test
    public void logDataSave() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                post("/api/logdatasave")
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
                post("/api/logdatasave")
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
        ArrayList<LogVO> list = new ArrayList<>();
        list.add(new LogVO("android3", "v", "MainActivity", "Hello", 1L, null, "ea0cc2e630ed4b6683252eb6114f89a9"));
//        given(logDataService.findByApiKeyAndPackageNameAndLevel("ea0cc2e630ed4b6683252eb6114f89a9", "android3", "v")).willReturn(list);
        when(logDataService.findByApiKeyAndPackageNameAndLevel("ea0cc2e630ed4b6683252eb6114f89a9", "android3", "v")).thenReturn(list);
        MockHttpServletResponse response = mvc.perform(
                get("/api/logdatalevelfilter/query?")
                        .header("secretKey", "ea0cc2e630ed4b6683252eb6114f89a9")
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
}
