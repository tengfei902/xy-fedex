package com.xy.fedex.facade.clients;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class RedisClientHolder {
    private JedisCluster jedis;
    @Value("redis.hosts")
    private List<HostAndPort> hosts;

    @PostConstruct
    public void init() {
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        if(CollectionUtils.isEmpty(hosts)) {
            throw new IllegalArgumentException("redis hosts not defined");
        }
        jedisClusterNodes.addAll(hosts);
        this.jedis = new JedisCluster(jedisClusterNodes);
    }

    public JedisCluster getRedisClient() {
        return jedis;
    }
}
