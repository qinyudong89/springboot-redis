package com.example;

import com.example.redis.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisApplicationTests {
    @Autowired
    private RedisUtil redisUtil;
    @Test
    void contextLoads() {
    }

    @Test
    void set() {
        redisUtil.set("test", "123");
    }

}
