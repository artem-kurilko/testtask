package com.testtask.resource.utils;

import org.springframework.web.client.RestTemplate;

public class RequestProcessor {

    public static String sendRequest(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
