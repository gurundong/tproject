// package com.gurundong.threadproject.thread.test01;

// public class Test {

//     public static final int MAX = 1000;

//     /**
//      *消息队列
//      */
//     private static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

//     /**
//      * 生产者当前生产的数据
//      */
//     private static AtomicInteger data = new AtomicInteger(1);

//     /**
//      * 累加器
//      */
//     private static AtomicInteger sum = new AtomicInteger(0);

//     /**
//      * 消费者当前消费次数
//      */
//     private static AtomicInteger time = new AtomicInteger(0);

//     /**
//      * 生产者线程池
//      */
//     private static ThreadPoolExecutor producerExecutor;

//     /**
//      * 消费者线程池
//      */
//     private static ThreadPoolExecutor consumerExecutor;

//     static {
//         producerExecutor = new ThreadPoolExecutor(15, 15, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
//             private AtomicInteger num = new AtomicInteger(0);

//             @Override
//             public Thread newThread(Runnable r) {
//                 return new Thread(r, num.incrementAndGet() + "");
//             }
//         });
//         consumerExecutor = new ThreadPoolExecutor(3, 3, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), (ThreadFactory) Thread::new);
//     }


//     public static void main(String[] args) {

//         for (int i = 0; i < 15; i++) {
//             producerExecutor.execute(new Producer(queue, data));
//         }
//         for (int i = 0; i < 3; i++) {
//             consumerExecutor.execute(new Consumer(queue,time, sum));
//         }
//         //加到1000了就关闭生产者，并等待剩余生产者任务结束
//         do {
//             if (data.get() >= MAX && !producerExecutor.isShutdown()) {
//                 producerExecutor.shutdown();
//             }
//         } while (!producerExecutor.isTerminated());
//         //取了1000次数据就关闭消费者，并等待消费者任务结束
//         while (true) {
//             if (time.get() >= MAX && !consumerExecutor.isShutdown()) {
//                 consumerExecutor.shutdown();
//                 break;
//             }
//         }
//         System.out.println(sum.get());
//     }

// }