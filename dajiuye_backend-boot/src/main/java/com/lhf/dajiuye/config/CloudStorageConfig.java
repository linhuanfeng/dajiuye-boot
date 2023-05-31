package com.lhf.dajiuye.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 云存储配置类
 */
@Data
@ConfigurationProperties(prefix = "oss.qiniu")
@Deprecated
public class CloudStorageConfig {
    private String domain;
    private String accesskey;
    private String secretKey;
    private String bucketName;
}
