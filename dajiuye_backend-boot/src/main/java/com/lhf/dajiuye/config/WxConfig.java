package com.lhf.dajiuye.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "wx")
public class WxConfig {
    String url;
    String appid;
    String secret;
    String grantType;
}
