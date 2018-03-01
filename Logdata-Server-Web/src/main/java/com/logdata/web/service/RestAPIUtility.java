package com.logdata.web.service;

import com.google.gson.Gson;
import com.logdata.common.model.LogDataListResponse;
import com.logdata.common.model.LogVO;
import com.logdata.common.model.SetDataListResponse;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

public class RestAPIUtility {
    private static final RestTemplate restTemplate = new RestTemplate();
    private static HttpHeaders headers = new HttpHeaders();
    private static Map<String, String> params = new HashMap<String, String>();
    private static HttpEntity<Object> entity = null;
    private static Gson gson = new Gson();

    public static ResponseEntity<Object> postData(String url, String secretKey, Object data) {
        try {
            URI uri = UriComponentsBuilder.newInstance()
                    .scheme("http")
                    .host("localhost")
                    .port(8081)
                    .path("/api" + url)
                    .build()
                    .encode()
                    .toUri();

            System.out.println(uri.toString());

            headers.set("secretKey", secretKey);
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            entity = new HttpEntity<>(data, headers);
            return restTemplate.postForEntity(uri, entity, Object.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static LogDataListResponse getLogDataLevel(String url, String secretKey, String packageName, String level) {
        try {
            URI uri = UriComponentsBuilder.newInstance()
                    .scheme("http")
                    .host("localhost")
                    .port(8081)
                    .path("/api" + url + "/query")
                    .queryParam("packagename", packageName)
                    .queryParam("level", level)
                    .build()
                    .encode()
                    .toUri();

            headers.set("secretKey", secretKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<LogVO[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, LogVO[].class);
            LogVO[] body = response.getBody();

            ArrayList<LogVO> list = new ArrayList<LogVO>(Arrays.asList(body));

            return new LogDataListResponse(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static LogDataListResponse getLogDataTag(String url, String secretKey, String packageName, String tag) {
        try {
            URI uri = UriComponentsBuilder.newInstance()
                    .scheme("http")
                    .host("localhost")
                    .port(8081)
                    .path("/api" + url + "/query")
                    .queryParam("packagename", packageName)
                    .queryParam("tag", tag)
                    .build()
                    .encode()
                    .toUri();

            headers.set("secretKey", secretKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<LogVO[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, LogVO[].class);
            LogVO[] body = response.getBody();

            ArrayList<LogVO> list = new ArrayList<LogVO>(Arrays.asList(body));

            return new LogDataListResponse(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static SetDataListResponse getData(String url, String secretKey) {
        try {
            URI uri = UriComponentsBuilder.newInstance()
                    .scheme("http")
                    .host("localhost")
                    .port(8081)
                    .path("/api" + url)
                    .build()
                    .encode()
                    .toUri();

            headers.set("secretKey", secretKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<Set> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Set.class);
            Set<String> body = response.getBody();

            return new SetDataListResponse(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static LogDataListResponse getData(String url, String secretKey, String packageName) {
        try {
            URI uri = UriComponentsBuilder.newInstance()
                    .scheme("http")
                    .host("localhost")
                    .port(8081)
                    .path("/api" + url + "/query")
                    .queryParam("packagename", packageName)
                    .build()
                    .encode()
                    .toUri();

            headers.set("secretKey", secretKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<LogVO[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, LogVO[].class);
            LogVO[] body = response.getBody();

            ArrayList<LogVO> list = new ArrayList<LogVO>(Arrays.asList(body));

            return new LogDataListResponse(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
