package com.gurundong.threadproject.thread.redis.lettuce;
import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class Test01 {
    public static void main(String[] args) {
        Test01 test01 = new Test01();
//        test01.test01();
        test01.test02();
    }

    /**
     * 最简单的同步使用方式
     */
    public void test01(){
        RedisURI redisURI = RedisURI.create("redis://1qaz2wsx@10.221.2.31:6379/0");
        RedisClient redisClient = RedisClient.create(redisURI);
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();
//        syncCommands.set("grd-service", "122233");
        String s = syncCommands.get("grd-service");
        System.out.println(s);
        connection.close();
        redisClient.shutdown();
    }

    /**
     * 异步方式调用redis
     * 基于netty实现的 EventLoop
     */
    public void test02(){
        RedisClient redisClient = RedisClient.create("redis://1qaz2wsx@10.221.2.31:6379/0");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisAsyncCommands<String, String> commands = connection.async();
        RedisFuture<String> redisFuture = commands.get("grd-service");
        try {
            String s = redisFuture.get(1, TimeUnit.SECONDS);
            System.out.println(s);
            System.out.println(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

    public void test03(){
        RedisClient redisClient = RedisClient.create("redis://1qaz2wsx@10.221.2.31:6379/0");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisAsyncCommands<String, String> commands = connection.async();
        RedisFuture<String> redisFuture = commands.get("grd-service");
        redisFuture.thenAcceptAsync(new Consumer<String>() {
            @Override
            public void accept(String s) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(s);
            }
        });
        System.out.println(2);
    }
}
