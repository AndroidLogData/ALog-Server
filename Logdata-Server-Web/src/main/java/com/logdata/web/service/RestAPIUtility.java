package com.logdata.web.service;

import com.logdata.common.model.*;
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
import java.util.Set;

@Component
public class RestAPIUtility {
    private final RestTemplate restTemplate;
    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity<Object> entity = null;
    private MultiValueMap<String, String> params = null;

    @Autowired
    public RestAPIUtility(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public ResponseEntity<Object> postData(String path, String url, String apiKey, Object data) {
        try {
            URI uri = uriBuilder(path, url);

            headers.set("apiKey", apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            entity = new HttpEntity<>(data, headers);
            return restTemplate.postForEntity(uri, entity, Object.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public LogVO[] getLogDataLevel(String url, String apiKey, String packageName, String level) {
        try {
            params = new LinkedMultiValueMap<String, String>();

            params.add("packagename", packageName);
            params.add("level", level);

            URI uri = uriBuilder("/api", url, params);

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<LogVO[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, LogVO[].class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public LogVO[] getLogDataTag(String url, String apiKey, String packageName, String tag) {
        try {
            params = new LinkedMultiValueMap<String, String>();

            params.add("packagename", packageName);
            params.add("tag", tag);

            URI uri = uriBuilder("/api", url, params);

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<LogVO[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, LogVO[].class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList getLogDataInfoSet(String url, String apiKey) {
        try {
            URI uri = uriBuilder("/api", url);

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<ArrayList> response = restTemplate.exchange(uri, HttpMethod.GET, entity, ArrayList.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Set getLogDataInfoSet(String url, String apiKey, String packageName) {
        try {
            params = new LinkedMultiValueMap<String, String>();

            params.add("packageName", packageName);

            URI uri = uriBuilder("/api", url, params);

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<Set> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Set.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public LogVO[] searchLogDataOfPackageName(String url, String apiKey, String packageName) {
        try {
            params = new LinkedMultiValueMap<String, String>();

            params.add("packagename", packageName);

            URI uri = uriBuilder("/api", url, params);

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<LogVO[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, LogVO[].class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public CrashVO getChoseCrashTimeData(String url, String apiKey, long time, String packageName) {
        try {
            params = new LinkedMultiValueMap<String, String>();

            params.add("time", String.valueOf(time));
            params.add("packageName", packageName);

            URI uri = uriBuilder("/api", url, params);

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<CrashVO> response = restTemplate.exchange(uri, HttpMethod.GET, entity, CrashVO.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public CrashVO getCrashData(String url, String apiKey, String packageName) {
        try {
            params = new LinkedMultiValueMap<String, String>();

            params.add("packageName", packageName);

            URI uri = uriBuilder("/api", url, params);

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<CrashVO> response = restTemplate.exchange(uri, HttpMethod.GET, entity, CrashVO.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList getCrashPackageNameList(String url, String apiKey) {
        try {
            URI uri = uriBuilder("/api", url);

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<ArrayList> response = restTemplate.exchange(uri, HttpMethod.GET, entity, ArrayList.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public CrashTimeVO[] getCrashTimeList(String url, String apiKey, String packageName) {
        try {
            params = new LinkedMultiValueMap<String, String>();

            params.add("packageName", packageName);

            URI uri = uriBuilder("/api", url, params);

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<CrashTimeVO[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, CrashTimeVO[].class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public LinkedHashMap<String, Integer> getCrashList(String url, String apiKey) {
        try {
            URI uri = uriBuilder("/help", url);

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<LinkedHashMap> response = restTemplate.exchange(uri, HttpMethod.GET, entity, LinkedHashMap.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public LogDataInfoVO[] getLogDataInfo(String url, String apiKey) {
        try {
            URI uri = uriBuilder("/main", url);

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<LogDataInfoVO[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, LogDataInfoVO[].class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Object deleteLogData(String url, String apiKey, String packageName) {
        try {
            params.add("packagename", packageName);

            URI uri = uriBuilder("/api", url, params);

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.DELETE, entity, Object.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public UserVO findUser(String url, String name) {
        try {
            params = new LinkedMultiValueMap<String, String>();

            params.add("name", name);

            URI uri = uriBuilder("/user", url, params);

            ResponseEntity<UserVO> response = restTemplate.getForEntity(uri, UserVO.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean userRegistration(String url, String userID, String password) {
        try {
            params = new LinkedMultiValueMap<String, String>();

            params.add("username", userID);
            params.add("password", password);

            URI uri = uriBuilder("/user", url, params);

            UserVO user = new UserVO(userID, password);

            ResponseEntity<Object> response = restTemplate.postForEntity(uri, user, Object.class);

            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();

            return Boolean.parseBoolean(String.valueOf(map.get("result")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Set<String> getCrashDataPackageName(String url, String apiKey) {
        try {
            URI uri = uriBuilder("/api", url);

            headers.set("apiKey", apiKey);
            entity = new HttpEntity<>(headers);

            ResponseEntity<Set> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Set.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
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
