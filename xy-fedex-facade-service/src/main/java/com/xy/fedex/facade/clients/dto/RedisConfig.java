package com.xy.fedex.facade.clients.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "redis.hosts")
@Data
@NoArgsConstructor
public class RedisConfig {
    private List<RedisHost> list;

    @Data
    public static class RedisHost {
        private String host;
        private int port;
    }
}
