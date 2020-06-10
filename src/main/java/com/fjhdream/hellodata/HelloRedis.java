package com.fjhdream.hellodata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class HelloRedis {
    @Autowired
    private RedisTemplate redisTemplate;
    public void test() {
        redisTemplate.opsForValue().set("{node1}redis1","1");
        System.out.println(redisTemplate.opsForValue().get("k1"));
    }
}
