package com.xy.fedex.facade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.*;

@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
public class FacadeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FacadeServiceApplication.class, args);
    }

}
