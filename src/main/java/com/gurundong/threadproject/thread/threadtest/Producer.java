package com.gurundong.threadproject.thread.threadtest;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {

        LinkedBlockingQueue<String> queue;
    
        private AtomicInteger data;
    
        Producer(LinkedBlockingQueue<String> queue,AtomicInteger data){
            this.queue=queue;
            this.data=data;
        }
    
        @Override
        public void run() {
    
            //小于1000就一直生产
            while (data.get() <= 1000) {
                String s = "第" + Thread.currentThread().getName() + "个生产者生产" + data.getAndIncrement();
                System.out.println(s);
                queue.offer(s);
            }
    
        }
}
