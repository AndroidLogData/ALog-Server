package com.logdata.web;

import com.logdata.common.model.LogDataListResponse;
import com.logdata.common.model.LogVO;
import com.logdata.web.service.RestAPIUtility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.test.web.client.ResponseCreator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withCreatedEntity;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@SpringBootTest
@RestClientTest(RestAPIUtility.class)
public class LogDataRestServiceServerTest {
    @Autowired
    private MockRestServiceServer server;
    @Autowired
    private RestAPIUtility restAPIUtility;

    private String baseUrl = "http://localhost:8081/api";

    @Test
    public void logDataPostTest() throws Exception {
        this.server.expect(
                requestTo(baseUrl + "/logdatasave"))
                .andRespond(
                        withSuccess(
                                "{\"result\":\"Log Data Transfer Success\"}",
                                MediaType.APPLICATION_JSON_UTF8
                        )
                );

        ResponseEntity response = restAPIUtility.postData("/logdatasave", "key", new LogVO());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
        assertThat(response.getBody().toString()).isEqualTo("{result=Log Data Transfer Success}");
    }

    @Test
    public void logDataLevelListTest() {
        ArrayList<LogVO> list = new ArrayList<>();
        list.add(new LogVO());

        this.server.expect(
                requestTo(baseUrl + "/logdatalevelfilter/query?packagename=android&level=i"))
                .andRespond(
                        withSuccess(
                                "[{}]",
                                MediaType.APPLICATION_JSON_UTF8
                        )
                );

        ResponseEntity response = restAPIUtility.getLogDataLevel("/logdatalevelfilter", "key", "android", "i");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
//        assertThat(response.getBody()).isEqualTo(list.toArray());
    }
}
