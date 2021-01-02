package com.gurundong.threadproject.thread.threadtest.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class T1 {
    public static void main(String[] args) {
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("1");
            }
        };
        Runnable task2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("2");
            }
        };
        ExecutorService pool = Executors.newFixedThreadPool(3);
        pool.execute(task1);
        pool.execute(task2);
        pool.shutdown();
    }
}
