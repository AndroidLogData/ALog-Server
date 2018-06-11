package com.logdata.web.service;

import com.logdata.common.logger.Logger;
import com.logdata.common.model.CrashTimeVO;
import com.logdata.common.model.CrashVO;
import com.logdata.common.model.LogDataInfoVO;
import com.logdata.common.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@Component
public class RestAPIManager {
    private final RestTemplate restTemplate;
    private HttpHeaders headers;
    private HttpEntity<Object> entity;
    private MultiValueMap<String, String> params;

    @Autowired
    public RestAPIManager(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
        headers = new HttpHeaders();
        params = new LinkedMultiValueMap<String, String>();
    }

    public ResponseEntity<Object> sendLogData(String apiKey, Object data) {
        try {
            URI uri = uriBuilder("/api", "/log-data");

            headers.set("apiKey", apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            entity = new HttpEntity<>(data, headers);
            return restTemplate.postForEntity(uri, entity, Object.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResponseEntity<Object> sendCrashData(String apiKey, Object data) {
        try {
            URI uri = uriBuilder("/api", "/crash");

            headers.set("apiKey", apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            entity = new HttpEntity<>(data, headers);
            return restTemplate.postForEntity(uri, entity, Object.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList getLogDataOfPackageName(String apiKey) {
        try {
            URI uri = uriBuilder("/api", "/log-data/package-name/set");

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<ArrayList> response = restTemplate.exchange(uri, HttpMethod.GET, entity, ArrayList.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public CrashVO getChoseCrashTimeData(String apiKey, long time, String packageName) {
        try {
            params.clear();
            params.add("time", String.valueOf(time));
            params.add("package-name", packageName);

            URI uri = uriBuilder("/api", "/crash/filter/time", params);

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<CrashVO> response = restTemplate.exchange(uri, HttpMethod.GET, entity, CrashVO.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public CrashVO getCrashData(String apiKey, String packageName) {
        try {
            params.clear();
            params.add("package-name", packageName);

            URI uri = uriBuilder("/api", "/crash/filter/package-name", params);

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<CrashVO> response = restTemplate.exchange(uri, HttpMethod.GET, entity, CrashVO.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public LinkedHashMap getCrashDataList(String apiKey, String packageName) {
        try {
            params.clear();
            params.add("package-name", packageName);

            URI uri = uriBuilder("/board", "/crash/detail", params);

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<LinkedHashMap> response = restTemplate.exchange(uri, HttpMethod.GET, entity, LinkedHashMap.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public CrashTimeVO[] getCrashTimeList(String apiKey, String packageName) {
        try {
            params.clear();
            params.add("package-name", packageName);

            URI uri = uriBuilder("/api", "/crash/package-name/time", params);

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<CrashTimeVO[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, CrashTimeVO[].class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public LogDataInfoVO[] getLogDataInfo(String apiKey) {
        try {
            URI uri = uriBuilder("/main", "/detail");

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<LogDataInfoVO[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, LogDataInfoVO[].class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public LogDataInfoVO getLogDataInfoOfPackageName(String apiKey, String packageName) {
        try {
            params.clear();
            params.add("package-name", packageName);

            URI uri = uriBuilder("/board", "/log-data/detail", params);

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<LogDataInfoVO> response = restTemplate.exchange(uri, HttpMethod.GET, entity, LogDataInfoVO.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Object deleteLogData(String apiKey, String packageName) {
        try {
            params.clear();
            params.add("package-name", packageName);

            URI uri = uriBuilder("/api", "/log-data", params);

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.DELETE, entity, Object.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public UserVO findUser(String name) {
        try {
            params.clear();
            params.add("name", name);

            URI uri = uriBuilder("/user", "/find", params);

            ResponseEntity<UserVO> response = restTemplate.getForEntity(uri, UserVO.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean userRegistration(String userID, String password) {
        try {
            params.clear();
            params.add("username", userID);
            params.add("password", password);

            URI uri = uriBuilder("/user", "/registration", params);

            UserVO user = new UserVO(userID, password);

            ResponseEntity<Object> response = restTemplate.postForEntity(uri, user, Object.class);

            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();

            return Boolean.parseBoolean(String.valueOf(map.get("result")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private URI uriBuilder(String path, String url) {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8081)
                .path(path + url)
                .build()
                .encode()
                .toUri();
    }

    private URI uriBuilder(String path, String url, MultiValueMap<String, String> params) {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8081)
                .path(path + url + "/query")
                .queryParams(params)
                .build()
                .encode()
                .toUri();
    }
}
