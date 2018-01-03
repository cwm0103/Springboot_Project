package com.bom.dataservice.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-18
 * Time: 11:22
 */
@Configuration
@EnableAutoConfiguration

public class RedisConfig {
   // private static Logger logger = Logger.getLogger(RedisConfig.class);

    @Value("${spring.redis.database}")
    private int  database;
    @Value("${spring.redis.hostName}")
    private String  hostName;
    @Value("${spring.redis.port}")
    private int  port;
    @Value("${spring.redis.password}")
    private String  password;
    @Value("${spring.redis.pool.maxActive}")
    private int  maxActive;
    @Value("${spring.redis.pool.maxWait}")
    private int  maxWait;
    @Value("${spring.redis.pool.maxIdle}")
    private int  maxIdle;
    @Value("${spring.redis.pool.minIdle}")
    private int  minIdle;
    @Value("${spring.redis.pool.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${spring.redis.pool.testOnReturn}")
    private boolean testOnReturn;
    @Value("${spring.redis.cluster.nodes}")
    private String nodes;

    @Bean
    public JedisPoolConfig getRedisConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setMaxWaitMillis(maxWait);

        return config;
    }
    @Bean
    public JedisCluster JedisClusterFactory() {
       // LOG.info("JedisCluster创建！！");
       // LOG.info("redis地址：" + host + ":" + port);
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        JedisPoolConfig jedisPoolConfig = getRedisConfig();
        String[] clusters=nodes.split("\\|");
        for (String cluster:clusters) {
            String[] hosts=cluster.split(":");
            jedisClusterNodes.add(new HostAndPort(hosts[0],Integer.parseInt(hosts[1])));
        }

        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, jedisPoolConfig);
        return jedisCluster;
    }

    @Bean
    public JedisConnectionFactory getConnectionFactory(){
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setDatabase(database);
        factory.setHostName(hostName);
        factory.setPassword(password);
        factory.setPort(port);

        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        //logger.info("JedisConnectionFactory bean init success.");
        return factory;
    }


    @Bean
    public RedisTemplate<?, ?> getRedisTemplate(){
        RedisTemplate<?,?> template = new StringRedisTemplate(getConnectionFactory());
        return template;
    }
}
