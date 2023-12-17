package com.shbone.reggiedemo.service.impl;

import com.shbone.reggiedemo.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author: SunHB
 * @createTime: 2023/12/12 上午12:52
 * @description:
 */
@Slf4j
@Component
public class RedisServiceImpl implements RedisService {
    private final long DURATION = 1 * 24 * 60 * 60 * 1000L;

    @Autowired
    private RedisTemplate redisTemplate;


    private ValueOperations<String,String> valueOperations;
    @PostConstruct
    public void init() {
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void set(String key, String value) {
        valueOperations.set(key,value,DURATION, TimeUnit.MILLISECONDS);
        log.info("key={}, value is: {} into redis cache", key, value);
    }

    @Override
    public String get(String key) {
        String redisValue = valueOperations.get(key);
        log.info("get from redis, value is: {}", redisValue);
        return redisValue;
    }

    @Override
    public boolean delete(String key) {
        Boolean result = redisTemplate.delete(key);
        log.info("delete from redis, key is: {}", key);
        return result;
    }

    @Override
    public Long getExpireTime(String key) {
        return valueOperations.getOperations().getExpire(key);
    }
}
