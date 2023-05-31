package com.lhf.dajiuye.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate的配置类
 * Spring提供了HttpComponentsClientHttpRequestFactory可以通过该方法来配置我们的http客户端
 */
@Configuration // 有组件依赖则proxyBeanMethods设为true  默认也是true
public class RestTemplateConfig {

    @Autowired
    private ApplicationValuesConfig appValues;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(httpRequestFactory());
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory(){
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    @Bean
    public HttpClient httpClient(){
//        RegistryBuilder<ConnectionSocketFactory>.create() // 为什么下面才不会报错
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(appValues.getMaxTotal());
        connectionManager.setDefaultMaxPerRoute(appValues.getMaxPerRoute());
        connectionManager.setValidateAfterInactivity(appValues.getValidateAfterInactivity());
        RequestConfig requestConfig = RequestConfig.custom()
                // 服务器返回数据（response）的时间，超出抛出read timeout
                .setSocketTimeout(appValues.getSocketTimeout())
                // 连接上服务器（握手成功）的时间，超出抛出connect timeout
                .setConnectTimeout(appValues.getConnectTimeout())
                // 从连接池中获取连接的超时时间，超时间未拿到可用连接，会抛出org.apache.http.coon.ConnectionPoolTimeoutException
                .setConnectionRequestTimeout(appValues.getConnectionRequestTimeout())
                // 创建
                .build();
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .build();
    }
}
