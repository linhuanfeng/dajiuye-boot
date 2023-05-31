package com.lhf.dajiuye.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "httppool")
public class ApplicationValuesConfig {
//    @Value("${http_pool.max_total}")
    private int maxTotal;

//    @Value("${http_pool.default_max_per_route}")
    private int maxPerRoute;

//    @Value("${http_pool.connect_timeout}")
    private int connectTimeout;

//    @Value("${http_pool.connection_request_timeout}")
    private int connectionRequestTimeout;

//    @Value("${http_pool.socket_timeout}")
    private int socketTimeout;

//    @Value("${http_pool.validate_after_inactivity}")
    private int validateAfterInactivity;
}
