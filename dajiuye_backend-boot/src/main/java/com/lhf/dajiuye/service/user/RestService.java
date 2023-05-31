package com.lhf.dajiuye.service.user;

import org.springframework.http.HttpEntity;

import java.util.Map;

public interface RestService {
    HttpEntity<Map<String, String>> generatePostJson(Map<String, String> jsonMap);
    String generateRequestParameters(String protocol, String uri, Map<String, String> params);
    String sendGet(String protocol, String uri, Map<String, String> uriMap);
    String sendPost(String uri, Map<String, String> jsonMap);
}
