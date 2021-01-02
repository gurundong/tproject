package com.gurundong.threadproject.thread.threadtest;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFutrue1 {
    public static void main(String[] args) {
        ExecutorService p = Executors.newCachedThreadPool();
        Future<Integer> result = p.submit(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                return new Random().nextInt();
            }
        });
        p.shutdown();
        System.out.println("get before");
        try {
            System.out.println(result.get());
            System.out.println("get after");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        ;
    }    
}
