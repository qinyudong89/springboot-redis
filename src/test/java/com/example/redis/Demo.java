package com.example.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Demo {
    private static final Logger LOGGER = LoggerFactory.getLogger(Demo.class);

    public Integer count = 0;

    public static void main(String[] args) {
        final Demo demo = new Demo();

        LOGGER.info("");
        Executor executor = Executors.newFixedThreadPool(10);
        for(int i=0;i<1000;i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    demo.count++;
                }
            });
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOGGER.info("final count value: {}", demo.count);
    }
}
