package com.example.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    //------------------------String类型 start-----------------------------------------

    /**
     * 设置 String 类型 key-value
     * 注：此方法没有设置超时，为了减少内存占用；请谨慎使用！！
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置 String 类型 key-value
     *
     * @param key      键
     * @param value    值
     * @param time     过期时间
     * @param timeUnit 时间单位
     */
    public void set(String key, Object value, long time, TimeUnit timeUnit) {
        if (time < 0) {
            //设置0-30秒的超时时间
            time = TimeOut.getRandom(30);
        }
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }
    void multiSet(Map<String, Object> map){
        redisTemplate.opsForValue().multiSet(map);
    }
    /**
     * 获取 String 类型 key-value
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置并返回设置的值
     * @param key
     * @param value
     * @return
     */
    Object getAndSet(String key, Object value){
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 如果key不存在，则设置key 的值为 value. 存在则不设置
     * 设置成功返回true 失败返回false
     * 使用 setnx
     *
     * @param key   键
     * @param value 值
     * @return
     */
    Boolean setIfAbsent(String key, Object value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 用于对值做自增操作
     *
     * @param key
     * @return
     */
    Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 用于对值做自减操作
     *
     * @param key
     * @return
     */
    Long decrement(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    //------------------------String类型 end-----------------------------------------


    //------------------------Hash类型 start-----------------------------------------

    /**
     * 设置 hash 类型 key-value
     *
     * @param key
     * @param field
     * @param value
     */
    void put(String key, Object field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 设置 Hash 类型 key-value
     * 如果key不存在，则设置key 的值为 value. 存在则不设置
     * 设置成功返回true 失败返回false
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Boolean putIfAbsent(String key, Object field, Object value) {
        return redisTemplate.opsForHash().putIfAbsent(key, field, value);
    }

    /**
     * 获取 Hash 类型 key-value
     *
     * @param key
     * @param field
     * @return
     */
    Object get(String key, Object field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取 Hash 类型 所有key下的value
     *
     * @param key
     * @return
     */
    List<Object> values(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 删除key下的field
     *
     * @param key
     * @param fields
     * @return
     */
    Long delete(String key, Object... fields) {
        return redisTemplate.opsForHash().delete(key, fields);
    }
    //------------------------Hash类型 end-----------------------------------------


    //------------------------List类型 start-----------------------------------------

    /**
     * 从左边插入元素
     *
     * @param key
     * @param value
     * @return
     */
    Long leftPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 从左边插入多个元素
     *
     * @param key
     * @param vars
     * @return
     */
    Long leftPushAll(String key, Collection<Object> vars) {
        return redisTemplate.opsForList().leftPushAll(key, vars);
    }

    /**
     * 从左边删除指定的key
     *
     * @param key
     * @return
     */
    Object leftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 从右边插入单个元素
     *
     * @param key
     * @param value
     * @return
     */
    Long rightPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 从右边插入多个元素
     *
     * @param key
     * @param vars
     * @return
     */
    Long rightPushAll(String key, Collection<Object> vars) {
        return redisTemplate.opsForList().leftPushAll(key, vars);
    }

    /**
     * 从右边删除元素
     *
     * @param key
     * @return
     */
    Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 获取列表指定索引下标的元素
     *
     * @param key
     * @param index
     * @return
     */
    Object index(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 按照索引范围修剪列表
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    List<Object> range(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 按照索引范围修剪列表
     *
     * @param key
     * @param start
     * @param end
     */
    void trim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 此方法使用redis中lrem命令会从列表中找到等于value的元素进行删除，根据count的不同
     * 分为三种情况：
     * count>0，从左到右，删除最多count个元素。
     * count<0，从右到左，删除最多count绝对值个元素。
     * count=0，删除所有。
     *
     * @param key
     * @param count
     * @param value
     * @return
     */
    Long remove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    //------------------------List类型 end-----------------------------------------


    //------------------------Set类型 start-----------------------------------------

    /**
     * @param key
     * @param vars
     * @return
     */
    Long add(String key, Object... vars) {
        return redisTemplate.opsForSet().add(key, vars);
    }

    /**
     * @param key
     * @param vars
     * @return
     */
    Long remove(String key, Object... vars) {
        return redisTemplate.opsForSet().remove(key, vars);
    }

    /**
     * 从集合中随机弹出一个元素
     *
     * @param key
     * @return
     */
    Object pop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }


    /**
     * 求keys之间的交集
     *
     * @param keys
     * @return
     */
    Set<Object> intersect(Collection<String> keys) {
        return redisTemplate.opsForSet().intersect(keys);
    }

    /**
     * 求keys之间的并集
     *
     * @param otherKeys
     * @return
     */
    Set<Object> union(Collection<String> otherKeys) {
        return redisTemplate.opsForSet().union(otherKeys);
    }

    /**
     * 求keys之间的差集
     *
     * @param keys
     * @return
     */
    Set<Object> difference(Collection<String> keys) {
        return redisTemplate.opsForSet().difference(keys);
    }

    /**
     * 获取集合下所有元素
     *
     * @param key
     * @return
     */
    Set<Object> members(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    //------------------------Set类型 end-----------------------------------------


    //------------------------ZSet类型 start-----------------------------------------

    /**
     * 添加
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    boolean add(String key, Object value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 返回指定的集合(分值排名：低至高)
     * @param key
     * @param start 开始下标
     * @param end 结束下标
     * @return
     */
    Set<Object> zsetRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 返回指定的集合（分值排名：高至低）
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set<Object> reverseRange(String key, long start, long end){
        return  redisTemplate.opsForZSet().reverseRange(key ,start, end);
    }

    /**
     * 获取min至max之间的元素
     * @param key
     * @param min
     * @param max
     * @return
     */
    Set<Object> rangeByScore(String key, double min, double max){
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    //------------------------ZSet类型 end-----------------------------------------
}
