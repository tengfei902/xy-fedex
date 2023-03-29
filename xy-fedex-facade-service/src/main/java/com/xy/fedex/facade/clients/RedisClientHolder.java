package com.xy.fedex.facade.clients;

import com.xy.fedex.facade.clients.dto.RedisConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RedisClientHolder {
//    private JedisCluster jedis;
    private Jedis jedis;
    @Autowired
    private RedisConfig redisConfig;

    @PostConstruct
    public void init() {
        if(CollectionUtils.isEmpty(redisConfig.getList())) {
            throw new IllegalArgumentException("redis hosts not defined");
        }
        List<HostAndPort> hosts = redisConfig.getList().stream().map(hostAndPort -> new HostAndPort(hostAndPort.getHost(),hostAndPort.getPort())).collect(Collectors.toList());
        this.jedis = new Jedis(hosts.get(0));
    }

    public Jedis getRedisClient() {
        return jedis;
    }
}
