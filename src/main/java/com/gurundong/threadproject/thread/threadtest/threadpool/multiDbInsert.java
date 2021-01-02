package com.gurundong.threadproject.thread.threadtest.threadpool;

import com.gurundong.threadproject.thread.common.utils.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.sql.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class multiDbInsert {
    public static Logger logger = LoggerFactory.getLogger(multiDbInsert.class);
    public static Logger serviceLogger = LoggerFactory.getLogger("serviceLog");
    public static Map<String, String> centerMap = new HashMap<>();
    public static List<String> centerList = new ArrayList<>();
    public static List<CenterDataDO> centerDo = new ArrayList<>();
    public static List<String> serviceLine = new ArrayList<>();

    public static void main(String[] args) {
        // 无界队列
        LinkedBlockingDeque queue = new LinkedBlockingDeque();
        ExecutorService pool = new ThreadPoolExecutor(50, 100, 10, TimeUnit.MINUTES, queue, new NameTreadFactory(), new MyIgnorePolicy());
        initCenterMap();
        initCenterList();
        getCenterIds();
        getServiceLine();

        for (int i = 0; i < 10000; i++) {
            int randomCenter = RandomUtil.getRandomInt(centerDo.size()-1);
            int randomService = RandomUtil.getRandomInt(1);
            int randomYear = RandomUtil.getRandomBetweenInt(-3,3);
            int year = LocalDate.now().plusYears(randomYear).getYear();
            int randomMonth = RandomUtil.getRandomBetweenInt(1,12);
            int randomDay = RandomUtil.getRandomBetweenInt(1,28);
            double randomFloat = RandomUtil.getRandomFloat(10000);
            CenterDataDO centerDataDO = centerDo.get(randomCenter);
            String service = serviceLine.get(randomService);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String timeYear = LocalDate.of(year, randomMonth, randomDay).format(fmt);
            String sql = "INSERT INTO `inspur_bss`.`contract_payment_complete_report`(`income_money`, `center_id`, `center_name`, `area_id`, `area_name`, `mu_id`, `mu_name`, `collection_date`, `service_line`, `created_time`, `updated_time`, `version`) VALUES ('"+randomFloat+"', '"+centerDataDO.getCenterId()+"', '"+centerDataDO.getCenterName()+"', '"+centerDataDO.getAreaId()+"', '"+centerDataDO.getAreaName()+"', '"+centerDataDO.getMuId()+"', '"+centerDataDO.getMuName()+"', '"+timeYear+"', '"+service+"', '2020-12-08 15:59:16', NULL, 1)";
            MyTask myTask = new MyTask("初始化report_complete",sql);
            pool.execute(myTask);
        }
        pool.shutdown();
    }

    private static  void getServiceLine() {
        serviceLine.add("cloudService");
        serviceLine.add("cloudProject");
    }
    private static  void getCenterIds() {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            String url = "jdbc:mysql://10.221.2.51:3306/inspur_bss";
            String username = "bss";
            String password = "bss0926";
            conn = DriverManager.getConnection(url, username, password);
            String sql = "SELECT c.id AS centerId,c.NAME AS centerName,m.id AS muId,m.`name` AS muName,a.id AS areaId,a.`code` AS areaName FROM product_cloud_center c LEFT JOIN product_mu m ON c.mu_id = m.id LEFT JOIN product_area a ON c.area_id = a.id WHERE c.STATUS = 'VALID'";
            st = conn.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            while (res.next()) {
                CenterDataDO centerDataDO = new CenterDataDO();
                centerDataDO.setAreaId(res.getString("areaId"));
                centerDataDO.setAreaName(res.getString("areaName"));
                centerDataDO.setMuId(res.getString("muId"));
                centerDataDO.setMuName(res.getString("muName"));
                centerDataDO.setCenterId(res.getString("centerId"));
                centerDataDO.setCenterName(res.getString("centerName"));
                centerDo.add(centerDataDO);
            }
        } catch (Exception e) {
            logger.error("error:{}", e);
        }finally {
            if(st != null){
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void initCenterMap() {
        centerMap.put("277","德阳");
        centerMap.put("1932","汝州");
        centerMap.put("275","攀枝花");
        centerMap.put("233","韶关");
        centerMap.put("3505","乌苏");
        centerMap.put("344","酒泉");
        centerMap.put("312693728950243328","阿拉山口");
        centerMap.put("340","天水");
        centerMap.put("264","河池");
        centerMap.put("174","东营");
        centerMap.put("94","哈尔滨");
        centerMap.put("87","四平");
        centerMap.put("144","宿州");
        centerMap.put("293","凉山州");
        centerMap.put("95","齐齐哈尔");
        centerMap.put("262","百色");
        centerMap.put("25","贵州省");
        centerMap.put("224","常德");
        centerMap.put("165","赣州");
        centerMap.put("143","阜阳");
        centerMap.put("164","鹰潭");
        centerMap.put("225","张家界");
        centerMap.put("229","怀化");
        centerMap.put("163","新余");
        centerMap.put("56","忻州");
        centerMap.put("3","天津");
        centerMap.put("59","呼和浩特");
        centerMap.put("42","保定");
        centerMap.put("29","甘肃省");
        centerMap.put("48","太原");
        centerMap.put("147","亳州");
        centerMap.put("1227","苏州吴中区");
        centerMap.put("6009","综改");
        centerMap.put("273","成都");
        centerMap.put("54","晋中");
        centerMap.put("156","南平");
        centerMap.put("282","乐山");
        centerMap.put("285","宜宾");
        centerMap.put("2161","利川");
        centerMap.put("10000","上海静安");
        centerMap.put("314","西双版纳");
        centerMap.put("103","牡丹江");
        centerMap.put("21","广西自治区");
        centerMap.put("24","四川省");
        centerMap.put("211","孝感");
        centerMap.put("180","日照");
        centerMap.put("172","淄博");
        centerMap.put("213","黄冈");
        centerMap.put("178","泰安");
        centerMap.put("168","抚州");
        centerMap.put("61","乌海");
        centerMap.put("230","娄底");
        centerMap.put("294","贵阳");
        centerMap.put("170","济南");
        centerMap.put("256","梧州");
        centerMap.put("336230367597821952","交付本部-其它");
        centerMap.put("352","海北州");
        centerMap.put("68","兴安盟");
        centerMap.put("175","烟台");
        centerMap.put("151","厦门");
        centerMap.put("303","昆明");
        centerMap.put("187","郑州");
        centerMap.put("2","北京");
        centerMap.put("238","江门");
        centerMap.put("51","长治");
        centerMap.put("790","满洲里");
        centerMap.put("22","海南省");
        centerMap.put("120","泰州");
        centerMap.put("194","焦作");
        centerMap.put("278","绵阳");
        centerMap.put("16","重庆");
        centerMap.put("176","潍坊");
        centerMap.put("23","山东省");
        centerMap.put("296","遵义");
        centerMap.put("173","枣庄");
        centerMap.put("186","菏泽");
        centerMap.put("183","德州");
        centerMap.put("30","青海省");
        centerMap.put("43","张家口");
        centerMap.put("3518","石河子");
        centerMap.put("202","周口");
        centerMap.put("253","南宁");
    }

    private static void initCenterList() {
        centerList.add("277");
        centerList.add("1932");
        centerList.add("275");
        centerList.add("233");
        centerList.add("3505");
        centerList.add("344");
        centerList.add("312693728950243328");
        centerList.add("340");
        centerList.add("264");
        centerList.add("174");
        centerList.add("94");
        centerList.add("87");
        centerList.add("144");
        centerList.add("293");
        centerList.add("95");
        centerList.add("262");
        centerList.add("25");
        centerList.add("224");
        centerList.add("165");
        centerList.add("143");
        centerList.add("164");
        centerList.add("225");
        centerList.add("229");
        centerList.add("163");
        centerList.add("56");
        centerList.add("3");
        centerList.add("59");
        centerList.add("42");
        centerList.add("29");
        centerList.add("48");
        centerList.add("147");
        centerList.add("1227");
        centerList.add("6009");
        centerList.add("273");
        centerList.add("54");
        centerList.add("156");
        centerList.add("282");
        centerList.add("285");
        centerList.add("2161");
        centerList.add("10000");
        centerList.add("314");
        centerList.add("103");
        centerList.add("21");
        centerList.add("24");
        centerList.add("211");
        centerList.add("180");
        centerList.add("172");
        centerList.add("213");
        centerList.add("178");
        centerList.add("168");
        centerList.add("61");
        centerList.add("230");
        centerList.add("294");
        centerList.add("170");
        centerList.add("256");
        centerList.add("336230367597821952");
        centerList.add("352");
        centerList.add("68");
        centerList.add("175");
        centerList.add("151");
        centerList.add("303");
        centerList.add("187");
        centerList.add("2");
        centerList.add("238");
        centerList.add("51");
        centerList.add("790");
        centerList.add("22");
        centerList.add("120");
        centerList.add("194");
        centerList.add("278");
        centerList.add("16");
        centerList.add("176");
        centerList.add("23");
        centerList.add("296");
        centerList.add("173");
        centerList.add("186");
        centerList.add("183");
        centerList.add("30");
        centerList.add("43");
        centerList.add("3518");
        centerList.add("202");
        centerList.add("253");
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
            logger.error(r.toString() + " rejected");
        }

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            doLog(r, executor);
        }
    }

    static class MyTask implements Runnable {
        static {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private String name;
        private String sql;

        public MyTask(String name, String sql) {
            this.name = name;
            this.sql = sql;
        }

        @Override
        public void run() {
            Connection conn = null;
            PreparedStatement st = null;
            try {
                String url = "jdbc:mysql://10.221.2.51:3306/inspur_bss";
                String username = "bss";
                String password = "bss0926";
                conn = DriverManager.getConnection(url, username, password);
                st = conn.prepareStatement(sql);
                int res = st.executeUpdate();
                if(res == 0){
                    logger.error("执行失败：{}",sql);
                }
            } catch (Exception e) {
                logger.error(sql);
                logger.error("error:{}", e);
            }finally {
                if(st != null){
                    try {
                        st.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(conn != null){
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
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

