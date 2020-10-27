package com.gurundong.threadproject.thread.test01.threadpool;

import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class multiThreadRequest {
    public static Logger logger = LoggerFactory.getLogger(multiThreadRequest.class);
    public static Logger serviceLogger = LoggerFactory.getLogger("serviceLog");
    public static void main(String[] args) {
        // 无界队列
        LinkedBlockingDeque queue = new LinkedBlockingDeque();
        ExecutorService pool = new ThreadPoolExecutor(30,30,10, TimeUnit.MINUTES,queue,new NameTreadFactory(),new MyIgnorePolicy());

        Map<String,String> centerMap = new HashMap<>();
//        centerMap.put("277","德阳");
//        centerMap.put("1932","汝州");
//        centerMap.put("275","攀枝花");
//        centerMap.put("233","韶关");
//        centerMap.put("3505","乌苏");
//        centerMap.put("344","酒泉");
//        centerMap.put("312693728950243328","阿拉山口");
//        centerMap.put("340","天水");
//        centerMap.put("264","河池");
//        centerMap.put("174","东营");
//        centerMap.put("94","哈尔滨");
//        centerMap.put("87","四平");
//        centerMap.put("144","宿州");
//        centerMap.put("293","凉山州");
//        centerMap.put("95","齐齐哈尔");
//        centerMap.put("262","百色");
//        centerMap.put("25","贵州省");
//        centerMap.put("224","常德");
//        centerMap.put("165","赣州");
//        centerMap.put("143","阜阳");
//        centerMap.put("164","鹰潭");
//        centerMap.put("225","张家界");
//        centerMap.put("229","怀化");
//        centerMap.put("163","新余");
//        centerMap.put("56","忻州");
//        centerMap.put("3","天津");
//        centerMap.put("59","呼和浩特");
//        centerMap.put("42","保定");
//        centerMap.put("29","甘肃省");
//        centerMap.put("48","太原");
//        centerMap.put("147","亳州");
//        centerMap.put("1227","苏州吴中区");
//        centerMap.put("6009","综改");
//        centerMap.put("273","成都");
//        centerMap.put("54","晋中");
//        centerMap.put("156","南平");
//        centerMap.put("282","乐山");
//        centerMap.put("285","宜宾");
//        centerMap.put("2161","利川");
//        centerMap.put("10000","上海静安");
//        centerMap.put("314","西双版纳");
//        centerMap.put("103","牡丹江");
//        centerMap.put("21","广西自治区");
//        centerMap.put("24","四川省");
//        centerMap.put("211","孝感");
//        centerMap.put("180","日照");
//        centerMap.put("172","淄博");
//        centerMap.put("213","黄冈");
//        centerMap.put("178","泰安");
//        centerMap.put("168","抚州");
//        centerMap.put("61","乌海");
//        centerMap.put("230","娄底");
//        centerMap.put("294","贵阳");
//        centerMap.put("170","济南");
//        centerMap.put("256","梧州");
//        centerMap.put("336230367597821952","交付本部-其它");
//        centerMap.put("352","海北州");
//        centerMap.put("68","兴安盟");
//        centerMap.put("175","烟台");
//        centerMap.put("151","厦门");
//        centerMap.put("303","昆明");


//                centerMap.put("187","郑州");
//                centerMap.put("2","北京");
//                centerMap.put("238","江门");
                centerMap.put("51","长治");
//                centerMap.put("790","满洲里");
//        centerMap.put("22","海南省");
//        centerMap.put("120","泰州");
//        centerMap.put("194","焦作");
//        centerMap.put("278","绵阳");
//                centerMap.put("16","重庆");
//        centerMap.put("176","潍坊");
//        centerMap.put("23","山东省");
//                centerMap.put("296","遵义");
//                centerMap.put("173","枣庄");
        //        centerMap.put("186","菏泽");
//                centerMap.put("183","德州");
        //        centerMap.put("30","青海省");
//                centerMap.put("43","张家口");
//                centerMap.put("3518","石河子");
//                centerMap.put("202","周口");
//                centerMap.put("253","南宁");


//        centerMap.put("fakeID-170-JNQX-201812-002","订单id:");
//        centerMap.put("fakeId-170-JNQX-201812-005","订单id:");
//        centerMap.put("BLORD-20190102-1874948","订单id:");
//        centerMap.put("fakeId-170-JNQX-202003-003","订单id:");
//        centerMap.put("fakeId-170-JNQX-201812-006","订单id:");
//        centerMap.put("fakeId-170-JNQX-201812-007","订单id:");
//        centerMap.put("JN-ORD-20190813-4731715","订单id:");
//        centerMap.put("fakeId-170-JNQX-201812-004","订单id:");
//        centerMap.put("fakeId-170-JNQX-201901-001","订单id:");
//        centerMap.put("fakeId-170-JNQX-201901-003","订单id:");
//        centerMap.put("fakeId-170-JNQX-201901-002","订单id:");
//        centerMap.put("fakeId-170-JNQX-201901-005","订单id:");
//        centerMap.put("fakeId-170-JNQX-201901-004","订单id:");
//        centerMap.put("fakeId-170-JNQX-201901-007","订单id:");
//        centerMap.put("fakeId-170-JNQX-201901-006","订单id:");
//        centerMap.put("fakeId-170-JNQX-201901-012","订单id:");
//        centerMap.put("fakeId-170-JNQX-201901-013","订单id:");
//        centerMap.put("fakeId-170-JNQX-201912-010","订单id:");
//        centerMap.put("fakeId-170-JNQX-201907-015","订单id:");
//        centerMap.put("BLORD-20190107-3152139","订单id:");


        // 查看应同步数量接口 http://api.inspurbss.inspurcloud.cn/deliver/contract/synchronize/getAllSyncDataSum?lastMonth=2020-07&forceUpdate=N
        String url = "http://api.inspurbss.inspurcloud.cn";
//        String url = "http://10.221.2.21:8766";
//        String billUrl = url+"/deliver/contract/synchronize/bill?month=2020-07&centerId=176&incomeOrBill=bill";
//        MyTask myTask = new MyTask("同步名称：,同步账单",billUrl);
//        pool.execute(myTask);
        for (Map.Entry entry : centerMap.entrySet()){
            String billUrl = url+"/deliver/contract/synchronize/bill?month=2020-09&centerId="+entry.getKey();
//            String orderUrl = url+"/deliver/contract/synchronize/order?month=2020-09&centerId="+entry.getKey();
            MyTask myTask = new MyTask("同步名称："+entry.getValue()+",id:"+entry.getKey()+",同步账单",billUrl);
//            MyTask myTask2 = new MyTask("同步名称："+entry.getValue()+",id:"+entry.getKey()+",同步订单",orderUrl);
            pool.execute(myTask);
//            pool.execute(myTask2);
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
        public void rejectedExecution(Runnable r, java.util.concurrent.ThreadPoolExecutor executor) {
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

