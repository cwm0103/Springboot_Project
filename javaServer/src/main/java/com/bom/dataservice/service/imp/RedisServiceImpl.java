package com.bom.dataservice.service.imp;

import com.bom.dataservice.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-18
 * Time: 13:19
 */
@Service
public class RedisServiceImpl implements IRedisService {
    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    @Autowired
    private JedisCluster jedisCluster;
    @Override
    public boolean set(String key, String value) {

        return false;
    }

    @Override
    public String get(String key) {
        String result=jedisCluster.get(key);

        return result;
    }
    @Override
    public List<String> mget(String keys){
        List<String>  result=jedisCluster.mget(keys);
        return result;
    }
    @Override
    public boolean expire(String key, long expire) {
        return false;
    }

    @Override
    public <T> boolean setList(String key, List<T> list) {
        return false;
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clz) {
        return null;
    }

    @Override
    public long lpush(String key, Object obj) {
        return 0;
    }

    @Override
    public long rpush(String key, Object obj) {
        return 0;
    }

    @Override
    public String lpop(String key) {
        return null;
    }
}
