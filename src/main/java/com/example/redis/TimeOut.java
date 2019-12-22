package com.example.redis;


import java.util.Random;

/**
 * 超时类
 */
public class TimeOut {
    //30秒
    public static final long SECONDS_30 = 30;
    //30分
    public static final long MINUTES_30 = SECONDS_30 * 2 * 30;
    //1小时
    public static final long HOURS_1 = MINUTES_30 * 2;

    private static final  Random RANDOM = new Random();

    /**
     * 生成随机数数
     * @param seed 加载因子
     * @return
     */
    public static long getRandom(int seed){
        long num = RANDOM.nextInt(1000 * seed );
        return num;
    }
}
