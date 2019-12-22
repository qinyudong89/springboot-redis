package com.example.redis;

import com.example.server.RedisStringServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RLockTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RLockTest.class);

    @Autowired
    private RedisStringServer redisStringServer;

    @Test
    public void lock() {
        Executor executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executor.execute(redisStringServer);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void bloomFilter(){
        redisStringServer.bloomFilter("filter", "a");
    }

}