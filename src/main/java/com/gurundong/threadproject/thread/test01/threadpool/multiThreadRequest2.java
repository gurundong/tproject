package com.gurundong.threadproject.thread.test01.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class multiThreadRequest2 {
    public static Logger logger = LoggerFactory.getLogger(multiThreadRequest2.class);
    public static Logger serviceLogger = LoggerFactory.getLogger("serviceLog");

    public static void main(String[] args) {
        // 无界队列
        LinkedBlockingDeque queue = new LinkedBlockingDeque();
        ExecutorService pool = new ThreadPoolExecutor(300,300,10, TimeUnit.MINUTES,queue,new NameTreadFactory(),new MyIgnorePolicy());
        for (int i = 0;i<10000;i++){
            String testUrl = "http://127.0.0.1:8081/test02/test01";

            MyTask myTask = new MyTask("同步名称："+i,testUrl);
            pool.execute(myTask);
        }
        pool.shutdown();
    }

    public static class NameTreadFactory implements ThreadFactory {

        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "my-thread-" + mThreadNum.getAndIncrement());
            System.out.println(t.getName() + " has been created");
            return t;
        }
    }

    public static class MyIgnorePolicy implements RejectedExecutionHandler {

        private void doLog(Runnable r, ThreadPoolExecutor e) {
            // 可做日志记录等
            logger.error( r.toString() + " rejected");
        }

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            doLog(r,executor);
        }
    }

    static class MyTask implements Runnable {
        private String name;
        private String url;

        public MyTask(String name,String url) {
            this.name = name;
            this.url = url;
        }

        @Override
        public void run() {
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setConnectTimeout(3600000);// 设置超时
            requestFactory.setReadTimeout(3600000);
            RestTemplate restTemplate = new RestTemplate(requestFactory);
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity<String> httpEntity = new HttpEntity<>("",headers);
            logger.info(getName()+"： interface call start");
            ResponseEntity<String> response = restTemplate.exchange(url,HttpMethod.GET,httpEntity,String.class);
            String resBody = response.getBody();
            serviceLogger.info(getName()+"： response: "+ resBody);
            logger.info(getName()+"： interface call end");
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "MyTask [name=" + name + "]";
        }
    }
}

