package com.logdata.web.service;

import com.logdata.common.model.*;
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

    public static SetDataListResponse getSetListData(String url, String secretKey) {
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

    public static LogDataListResponse getPackageNameList(String url, String secretKey, String packageName) {
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

    public static CrashVO getCrashTimeData(String url, String secretKey, long time, String packageName) {
        try {
            URI uri = UriComponentsBuilder.newInstance()
                    .scheme("http")
                    .host("localhost")
                    .port(8081)
                    .path("/api" + url + "/query")
                    .queryParam("time", time)
                    .queryParam("packageName", packageName)
                    .build()
                    .encode()
                    .toUri();

            headers.set("secretKey", secretKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<CrashVO> response = restTemplate.exchange(uri, HttpMethod.GET, entity, CrashVO.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static CrashVO getCrashTime(String url, String secretKey, String packageName) {
        try {
            URI uri = UriComponentsBuilder.newInstance()
                    .scheme("http")
                    .host("localhost")
                    .port(8081)
                    .path("/api" + url + "/query")
                    .queryParam("packageName", packageName)
                    .build()
                    .encode()
                    .toUri();

            headers.set("secretKey", secretKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<CrashVO> response = restTemplate.exchange(uri, HttpMethod.GET, entity, CrashVO.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList getCrashPackageNameList(String url, String secretKey) {
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

            ResponseEntity<ArrayList> response = restTemplate.exchange(uri, HttpMethod.GET, entity, ArrayList.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList getCrashTimeList(String url, String secretKey, String packageName) {
        try {
            URI uri = UriComponentsBuilder.newInstance()
                    .scheme("http")
                    .host("localhost")
                    .port(8081)
                    .path("/api" + url + "/query")
                    .queryParam("packageName", packageName)
                    .build()
                    .encode()
                    .toUri();

            headers.set("secretKey", secretKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<CrashTimeVO[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, CrashTimeVO[].class);

            ArrayList<CrashTimeVO> list = new ArrayList<CrashTimeVO>(Arrays.asList(response.getBody()));

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
