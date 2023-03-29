package com.xy.fedex.facade;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableDubbo
@EnableConfigurationProperties
@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
public class FacadeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FacadeServiceApplication.class, args);
    }

}
