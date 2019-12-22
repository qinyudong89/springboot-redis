package com.example.redis;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 防止缓存穿透
 */
public class PreventCachepenetration {
    private static final Logger LOGGER = LoggerFactory.getLogger(PreventCachepenetration.class);

    @Autowired
    RedisUtil redisUtil;

    public Object save(String key) {
        Object obj = redisUtil.get(key);
        if (obj != null) {
            return obj;
        } else {
            //
        }
        return null;
    }


    public static void bloomFilter(long size) {

        BloomFilter<Long> bloomFilter = BloomFilter.create(Funnels.longFunnel(), size);
        for (long i = 0; i < size; i++) {
            bloomFilter.put(i);
        }

        for (long i = 0; i < size; i++) {
            if (!bloomFilter.mightContain(i)) {
                System.out.println("有坏人逃脱了");
            }
        }
        error(bloomFilter, size);
    }

    public static void error(BloomFilter bloomFilter, long size) {
        List<Long> list = new ArrayList<Long>(1000);
        for (long i = size + 10000; i < size + 20000; i++) {
            if (bloomFilter.mightContain(i)) {
                list.add(i);
            }
        }
        System.out.println("有误伤的数量：" + list.size());
    }

    public static void main(String[] args) {
        long size = 1000000000L;
        PreventCachepenetration.bloomFilter(size);

    }
}
