package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisDao {

    @Autowired
    private StringRedisTemplate template;

    public void setKey(String key,String value){
        this.template.opsForValue().set(key, value, 1, TimeUnit.MINUTES);//1分钟过期
    }

    public String getValue(String key){
        return this.template.opsForValue().get(key);
    }
}

