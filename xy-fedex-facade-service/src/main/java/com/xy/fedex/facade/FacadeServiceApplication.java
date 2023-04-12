package com.xy.fedex.facade;

import com.xy.fedex.facade.utils.ApplicationContextUtils;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations = "classpath:spring.xml")
@ComponentScan(basePackages = "com.xy")
@EnableDubbo
@EnableConfigurationProperties
@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
public class FacadeServiceApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(FacadeServiceApplication.class, args);
//        ApplicationContextUtils.setApplicationContext(applicationContext);
    }

}
