package com.gurundong.threadproject.thread.threadtest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallableAndFutrue3 {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        try {
			String a = pool.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					System.out.println("111");
					Thread.sleep(10);
					return "hh";
				}
			}).get();
			System.out.println(a);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
    }    
}
