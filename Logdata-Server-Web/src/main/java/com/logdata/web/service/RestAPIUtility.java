package com.logdata.web.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class RestAPIUtility {
    private static final RestTemplate restTemplate = new RestTemplate();
    private static HttpHeaders headers = new HttpHeaders();
    private static Map<String, String> params = new HashMap<String, String>();
    private static HttpEntity<Object> entity = null;

    public static ResponseEntity<Object> postData(String url, String secretKey, Object data) {
        try {
            headers.set("secretKey", secretKey);
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            entity = new HttpEntity<>(data, headers);
            return restTemplate.postForEntity("http://192.168.0.7:8081/api" + url, entity, Object.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object getData(String url, String secretKey) {
        try {
            headers.set("secretKey", secretKey);
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            entity = new HttpEntity<>(headers);
            return restTemplate.getForObject("http://192.168.0.7:8081/api" + url, Object.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void getData(String url, String secretKey, String packageName) {
        try {
            URI uri = UriComponentsBuilder.newInstance()
                    .scheme("http")
                    .host("192.168.0.7")
                    .port(8081)
                    .path("/api" + url + "/query")
                    .queryParam("packagename", packageName)
                    .build()
                    .encode()
                    .toUri();

            System.out.println(uri.toString());

            headers.set("secretKey", secretKey);
            entity = new HttpEntity<>(headers);
            System.out.println(restTemplate.getForObject(uri, String.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

//        return null;
    }
}
