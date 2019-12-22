package com.example.server;

import com.example.redis.RedisUtil;
import com.example.redis.TimeOut;
import com.google.common.hash.BloomFilter;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class RedisStringServer implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisStringServer.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedissonClient redissonClient;

    public static Integer count = 0;

    /**
     * 手机验证码设置过期时间
     *
     * @param mobilePhone
     * @return
     */
    public int getCode(String mobilePhone) {
        Object code = redisUtil.get(mobilePhone);
        if (code == null) {
            Random random = new Random();
            code = random.nextInt(10000);
            redisUtil.set(mobilePhone, code, TimeOut.SECONDS_30, TimeUnit.SECONDS);
        }
        return (int) code;
    }

    @Override
    public void run() {
        RLock lock = null;
        try {
            lock = redissonClient.getLock("incr");
            // 3. 尝试加锁，最多等待3秒，上锁以后10秒自动解锁
            lock.lock(3, TimeUnit.SECONDS);
            for (int i = 0; i < 100000; i++) {
                count++;
            }
        }catch (Exception e){
            LOGGER.error( "分布式锁异常：{}", e);
        }finally {
            lock.unlock();
        }
        LOGGER.info(Thread.currentThread().getId() + ",获取锁成功: {}", count);
    }

    public void bloomFilter(String key, String val){
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter(key);
        // 初始化布隆过滤器，预计统计元素数量为55000000，期望误差率为0.03
        bloomFilter.tryInit(55000000L, 0.03);
        bloomFilter.add(val);
        boolean flag = bloomFilter.contains(val);
        LOGGER.info("结果: {}", flag);
    }
}
