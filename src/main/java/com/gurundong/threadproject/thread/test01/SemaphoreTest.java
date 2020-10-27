package com.gurundong.threadproject.thread.test01;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    public static void main(String[] args){
//        Random random = new Random();
//        int ra = random.nextInt(10000);
//        System.out.println(ra);
        test01();
    }

    public static void test01() {
        System.out.println("Semaphore初测");
        Semaphore window = new Semaphore(5);
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            new Thread() {
                @Override
                public void run() {
                    try {
                        window.acquire();
                        System.out.println("客户 " + finalI + ":开始购票");
                        Random random = new Random();
                        int ran = random.nextInt(10000);
                        sleep(ran);
                        System.out.println("客户 " + finalI + ":结束购票");
                        window.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
