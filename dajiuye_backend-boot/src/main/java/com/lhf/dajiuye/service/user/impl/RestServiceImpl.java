package com.lhf.dajiuye.service.user.impl;

import com.lhf.dajiuye.service.user.RestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * http客户端，实现发送http(s)请求
 */
@Slf4j
@Service // 放入容器，可供其它服务注入
public class RestServiceImpl implements RestService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 生产post请求的JOSN请求参数
     * @param jsonMap
     * @return
     */
    @Override
    public HttpEntity<Map<String, String>> generatePostJson(Map<String, String> jsonMap) {
        // 如果需要其他请求头信息，这里也可以追加
        HttpHeaders httpHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
        httpHeaders.setContentType(type);
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(jsonMap, httpHeaders);
        return httpEntity;
    }

    /**
     * 生成get参数请求url
     * @param protocol
     * @param uri
     * @param params
     * @return
     */
    @Override
    public String generateRequestParameters(String protocol, String uri, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(protocol).append("://").append(uri);
        if(params!=null&&!params.isEmpty()){
            sb.append("?");
            for (Map.Entry<String, String> map : params.entrySet()) {
                sb.append(map.getKey())
                        .append("=")
                        .append(map.getValue())
                        .append("&");
            }
            uri=sb.substring(0,sb.length()-1);
            return uri;
        }
        return sb.toString();
    }

    /**
     * 返回session_key和openid的json字符串
     * @return
     */
    @Override
    public String sendGet(String protocol,String uri,Map<String,String> uriMap) {
        // 获取session_key和openId
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                generateRequestParameters(protocol,uri , uriMap),
                String.class
        );
        // {"session_key":"IMKydnftHZYMXgiPEOMTlw==","openid":"o36Ce6qjFehGRq2IpFIg_9hkmleM(不变)"}
        log.info("responseEntity:==================={}",responseEntity);
        return responseEntity.getBody();
    }

    /**
     * post请求，请求参数为json
     * @return
     */
    @Override
    public String sendPost(String uri,Map<String,String> jsonMap) {

        ResponseEntity<String> apiResponse = restTemplate.postForEntity(
                uri,
                generatePostJson(jsonMap),
                String.class
        );
        return apiResponse.getBody();
    }
}
