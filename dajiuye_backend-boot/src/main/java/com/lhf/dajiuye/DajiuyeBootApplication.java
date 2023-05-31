package com.lhf.dajiuye;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableCaching // 开启缓存
@SpringBootApplication
@EnableSwagger2
@MapperScan("com.lhf.dajiuye.mapper")
public class DajiuyeBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DajiuyeBootApplication.class, args);
    }

}
