package com.gurundong.threadproject.thread.threadtest;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Comsumer implements Runnable{

        private LinkedBlockingQueue<String> queue;
    
        private AtomicInteger sum;
    
        private AtomicInteger time;
    
        Comsumer(LinkedBlockingQueue<String> queue,AtomicInteger time,AtomicInteger sum){
            this.queue=queue;
            this.time=time;
            this.sum=sum;
        }
    
        @Override
        public void run() {
            while (time.get() != 1000) {
                String poll = queue.poll();
                if (poll != null) {
                    sum.getAndAdd(Integer.parseInt(poll.substring(poll.lastIndexOf("产") + 1)));
                    time.getAndIncrement();
                }
            }
        }
}
