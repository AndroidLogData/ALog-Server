package com.logdata.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logdata.api.sevice.LogDataService;
import com.logdata.api.sevice.PackageNameDataService;
import com.logdata.common.model.LogVO;
import com.logdata.common.model.PackageNameVO;
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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LogDataControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private LogDataService logDataService;
    @MockBean
    private PackageNameDataService packageNameDataService;
    @Autowired
    private ObjectMapper objectMapper;
    private String logDataJsonString;

    private ArrayList<LogVO> logDataList;
    private PackageNameVO packageNameData;

    @Before
    public void setUp() throws JsonProcessingException {
        LogVO logData = new LogVO("android3", "v", "MainActivity", "Hello", 1L, null);
        logDataList = new ArrayList<>();
        logDataList.add(logData);

        packageNameData = new PackageNameVO("key");
        packageNameData.getPackageNameList().add("[MainActivity]");

        logDataJsonString = objectMapper.writeValueAsString(logData);
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void logDataSave() throws Exception {
        when(packageNameDataService.findPackageNameVOByApiKey("key")).thenReturn(packageNameData);
        MockHttpServletResponse response = mvc.perform(
                post("/api/log-data")
                        .header("apiKey", "key")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(logDataJsonString)
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
                post("/api/log-data")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{}")
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getErrorMessage()).isEqualTo("Missing request header 'apiKey' for method parameter of type String");
    }

    @Test
    public void logDataLevelFilterTest() throws Exception {
        when(packageNameDataService.findPackageNameVOByApiKey("key")).thenReturn(packageNameData);
        when(logDataService.findByPackageNameAndLevel("android3", "v", new Sort(Sort.Direction.DESC, "time"))).thenReturn(logDataList);
        MockHttpServletResponse response = mvc.perform(
                get("/api/log-data/filter/level/query")
                        .header("apiKey", "key")
                        .param("package-name", "android3")
                        .param("level", "v")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    public void logDataTagFilterTest() throws Exception {
        when(packageNameDataService.findPackageNameVOByApiKey("key")).thenReturn(packageNameData);
        when(logDataService.findByPackageNameAndTag("android3", "[MainActivity::onCreate]", new Sort(Sort.Direction.ASC, "time"))).thenReturn(logDataList);
        MockHttpServletResponse response = mvc.perform(
                get("/api/log-data/filter/tag/query")
                        .header("apiKey", "key")
                        .param("package-name", "android3")
                        .param("tag", "[MainActivity::onCreate]")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

//    @Test
//    public void getTagTest() throws Exception {
//        when(logDataService.findByApiKey("key")).thenReturn(logDataList);
//        MockHttpServletResponse response = mvc.perform(
//                get("/api/log-data/tag/set")
//                        .header("apiKey", "key")
//                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//        )
//                .andDo(print())
//                .andReturn()
//                .getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        assertThat(response.getContentAsString()).isEqualTo("[\"MainActivity\"]");
//    }

    @Test
    public void getPackageNameTest() throws Exception {
//        when(logDataService.findByApiKey("key")).thenReturn(logDataList);
        when(packageNameDataService.findPackageNameVOByApiKey("key")).thenReturn(packageNameData);
        MockHttpServletResponse response = mvc.perform(
                get("/api/log-data/package-name/set")
                        .header("apiKey", "key")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo("[\"[MainActivity]\"]");
    }

    @Test
    public void logDataPackageNameListTest() throws Exception {
        when(packageNameDataService.findPackageNameVOByApiKey("key")).thenReturn(packageNameData);
        MockHttpServletResponse response = mvc.perform(
                get("/api/log-data/filter/package-name/query")
                        .header("apiKey", "key")
                        .param("package-name", "android3")
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
        when(logDataService.findAll()).thenReturn(logDataList);
        MockHttpServletResponse response = mvc.perform(
                get("/api/log-data/list")
                        .header("apiKey", "key")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo("[{\"packageName\":\"android3\",\"level\":\"v\",\"tag\":\"MainActivity\",\"message\":\"Hello\",\"time\":1,\"memoryInfo\":null}]");
    }
}