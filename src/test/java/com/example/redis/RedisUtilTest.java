package com.example.redis;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
class RedisUtilTest {
    @Autowired
    RedisUtil redisUtil;

    @Test
    void set() {
        for (int i = 1; i < 100000; i++) {
            redisUtil.set("user:id:" + i, i);
        }

    }

    @Test
    void set1() {
        redisUtil.set("count", 1,2000, TimeUnit.SECONDS);
    }

    @Test
    void get() {
    }

    @Test
    void multiSet(){
        Map map = new HashMap();
        for (int i = 1; i < 100000; i++) {
            map.put("user:id:" + i, i);
        }
        redisUtil.multiSet(map);
    }

    @Test
    void setIfAbsent() {
    }

    @Test
    void increment() {
        long count = redisUtil.increment("count");
        System.out.println(count);
    }

    @Test
    void decrement() {
        long count = redisUtil.decrement("count");
        System.out.println(count);
    }

    @Test
    void put() {
        redisUtil.put("user:1", "name", "Tom");
        Object obj = redisUtil.get("user:1", "name");
        System.out.println(obj);
    }

    @Test
    void values() {
        List<Object> list = redisUtil.values("user:1");
        System.out.println(list);
    }

    @Test
   void putIfAbsent(){
        boolean flag = redisUtil.putIfAbsent("user:1", "name", "Jack");
        System.out.println(flag);
    }

    @Test
    void delete(){
        Object[] objects = new Object[2];
        objects[0] = "name";
        objects[1] = "id";
        long count = redisUtil.delete("user:1", objects);
        System.out.println(count);
    }

    @Test
    void leftPush(){
        redisUtil.leftPush("list", "b");
    }

    @Test
    void leftPushAll(){
        List vars = new ArrayList();
        vars.add("b");
        vars.add("c");
        vars.add("d");
        redisUtil.leftPushAll("list",  vars);
    }

    @Test
    void range(){
        System.out.println(redisUtil.range("list", 0, -1));
    }

    @Test
    void index(){
        System.out.println(redisUtil.index("list", 2));
    }

    @Test
    void trim(){
        redisUtil.trim("list", 1,2);
    }

    @Test
    void leftPop(){
        redisUtil.leftPop("list");
    }

    @Test
    void add(){
        String[] ali = {"it", "news", "ent", "sports"};
        redisUtil.add("user:2:follow", ali);


    }

    @Test
    void pop(){
        System.out.println( redisUtil.pop("set"));;
    }

    @Test
    void union(){
        List otherKeys = new ArrayList();
        otherKeys.add("user:1:follow");
        otherKeys.add("user:2:follow");
        Set<Object> setUnion = redisUtil.union(otherKeys);
        System.out.println(setUnion);
    }

    @Test
    void intersect(){
        List otherKeys = new ArrayList();
        otherKeys.add("user:1:follow");
        otherKeys.add("user:2:follow");
        System.out.println(redisUtil.intersect(otherKeys));
        //System.out.println(redisUtil.intersect("user:1:follow", "user:2:follow"));;
    }

    @Test
    void members(){
        System.out.println(redisUtil.members("user:1:follow"));
    }

    @Test
    void zsetAdd(){
//kris、mike、frank、tim、martin、tom
        //1、91、200、220、250、251，
        redisUtil.add("userRanking", "kris", 1);
        redisUtil.add("userRanking", "mike", 91);
        redisUtil.add("userRanking", "frank", 200);
        redisUtil.add("userRanking", "tim", 220);
        redisUtil.add("userRanking", "martin", 250);
        redisUtil.add("userRanking", "tom", 251);
    }

    @Test
    void zsetRange(){
        System.out.println(redisUtil.zsetRange("userRanking", 0, 2));
    }

    @Test
    void reverseRange(){
        System.out.println(redisUtil.reverseRange("userRanking", 0 , 2));
    }

}