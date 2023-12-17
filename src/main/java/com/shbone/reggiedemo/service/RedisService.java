package com.shbone.reggiedemo.service;

/**
 * @author: SunHB
 * @createTime: 2023/12/12 上午12:49
 * @description:
 */
public interface RedisService {
    void set(String key,String value);
    String get(String key);
    boolean delete(String key);
    Long getExpireTime(String key);
}
