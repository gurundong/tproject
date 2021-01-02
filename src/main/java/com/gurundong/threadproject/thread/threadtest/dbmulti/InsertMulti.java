package com.gurundong.threadproject.thread.threadtest.dbmulti;

import com.gurundong.threadproject.thread.common.utils.RandomUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 测试表结构
 * CREATE TABLE `contract_payment_complete_report_copy1` (
 *   `id` int(11) NOT NULL AUTO_INCREMENT,
 *   `income_money` decimal(16,2) NOT NULL,
 *   `center_id` bigint(20) NOT NULL,
 *   `center_name` varchar(60) DEFAULT NULL,
 *   `area_id` bigint(20) DEFAULT NULL,
 *   `area_name` varchar(60) DEFAULT NULL,
 *   `mu_id` bigint(20) DEFAULT NULL,
 *   `mu_name` varchar(60) DEFAULT NULL,
 *   `collection_date` date NOT NULL,
 *   `service_line` varchar(20) NOT NULL,
 *   `created_time` datetime DEFAULT NULL,
 *   `updated_time` datetime DEFAULT NULL,
 *   `version` int(20) DEFAULT NULL,
 *   PRIMARY KEY (`id`),
 * ) ENGINE=InnoDB AUTO_INCREMENT=487 DEFAULT CHARSET=utf8;
 */


/**
 * 批量的方式，使用SHOW VARIABLES LIKE 'max_allowed_packet' 查看最大单次通信包大小。单次通信大于此数会报错：
 * Caused by: com.mysql.cj.jdbc.exceptions.PacketTooBigException: Packet for query is too large (4,700,259 > 4,194,304). You can change this value on the server by setting the 'max_allowed_packet' variable.
 * 建议1万-5万条数据提交一次
 */
public class InsertMulti {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String url = "jdbc:mysql://10.221.2.51:3306/inspur_bss?rewriteBatchedStatements=true";
    private String user = "bss";
    private String password = "bss0926";

    public static void main(String[] args) {
        InsertMulti insertMulti = new InsertMulti();
//        insertMulti.test01();
//        insertMulti.test02();
//        insertMulti.test03();
//        insertMulti.test04();
        insertMulti.test05();
    }

    // 普通插入方式
    // 1000条数据，时间113182毫秒，113秒
    public void test01(){
        Connection conn = null;
        PreparedStatement pstm =null;
        ResultSet rt = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            float income_money = RandomUtil.getRandomBetweenFloat(0, 10000);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.of(1980, 1, 1);
            Long startTime = System.currentTimeMillis();
            String sql = "INSERT INTO `inspur_bss`.`contract_payment_complete_report_copy1`(`income_money`, `center_id`, `center_name`, `area_id`, `area_name`, `mu_id`, `mu_name`, `collection_date`, `service_line`, `created_time`, `updated_time`, `version`) " +
                    "VALUES (?, 2, '北京', 202006050015, '华北区域', 19, '北京', ?, 'cloudService', '2020-12-22 18:51:48', NULL, 1)";
            pstm = conn.prepareStatement(sql);
            for (int i = 0; i < 1000; i++) {
                String datestr = date.plusDays(i).format(fmt);
                pstm.setFloat(1, income_money);
                pstm.setString(2, datestr);
                pstm.executeUpdate();
            }
            Long endTime = System.currentTimeMillis();
            System.out.println("用时：" + (endTime - startTime));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(pstm!=null){
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // 事务提交插入方式
    // 1000条数据，时间28731毫秒，28秒
    public void test02(){
        Connection conn = null;
        PreparedStatement pstm =null;
        ResultSet rt = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            float income_money = RandomUtil.getRandomBetweenFloat(0, 10000);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.of(1980, 1, 1);
            Long startTime = System.currentTimeMillis();
            conn.setAutoCommit(false);
            String sql = "INSERT INTO `inspur_bss`.`contract_payment_complete_report_copy1`(`income_money`, `center_id`, `center_name`, `area_id`, `area_name`, `mu_id`, `mu_name`, `collection_date`, `service_line`, `created_time`, `updated_time`, `version`) " +
                    "VALUES (?, 2, '北京', 202006050015, '华北区域', 19, '北京', ?, 'cloudService', '2020-12-22 18:51:48', NULL, 1)";
            pstm = conn.prepareStatement(sql);
            for (int i = 0; i < 1000; i++) {
                String datestr = date.plusDays(i).format(fmt);
                pstm.setFloat(1, income_money);
                pstm.setString(2, datestr);
                pstm.executeUpdate();
            }
            conn.commit();
            Long endTime = System.currentTimeMillis();
            System.out.println("用时：" + (endTime - startTime));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(pstm!=null){
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // 批量插入
    // 需要加?rewriteBatchedStatements=true参数
    // 1000条数据，时间1237毫秒，1.2秒
    // 100000,package = 24000,时间13209毫秒，13秒
    // 1000000,package = 24000,时间108285毫秒，108秒
    // 1000000,package = 10000,时间137290毫秒，137秒
    // 1000000,package = 100000,时间102009毫秒，102秒
    // 1000000,package = 50000,时间102677毫秒，102秒
    public void test03(){
        Connection conn = null;
        PreparedStatement pstm =null;
        ResultSet rt = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            float income_money = RandomUtil.getRandomBetweenFloat(0, 10000);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.of(1980, 1, 1);
            Long startTime = System.currentTimeMillis();
            String sql = "INSERT INTO `inspur_bss`.`contract_payment_complete_report_copy1`(`income_money`, `center_id`, `center_name`, `area_id`, `area_name`, `mu_id`, `mu_name`, `collection_date`, `service_line`, `created_time`, `updated_time`, `version`) " +
                    "VALUES (?, 2, '北京', 202006050015, '华北区域', 19, '北京', ?, 'cloudService', '2020-12-22 18:51:48', NULL, 1)";
            int packet = 25000;
            int size = 1000000;
            int j = 0;
            int n1 =  size / packet;
            int n2 =  size % packet;
            int num = (n2 == 0) ? n1 : n1+1;
            pstm = conn.prepareStatement(sql);
            for (int i = 0; i < num; i++) {
                int packetPer = 0;
                if(n2 != 0 && i == num-1){
                    packetPer = n2;
                }else {
                    packetPer = packet;
                }
                // 每packet包大小，向mysql传输一次
                for (int k = 0; k < packetPer; k++) {
                    String datestr = date.plusDays(j).format(fmt);
                    pstm.setFloat(1, income_money);
                    pstm.setString(2, datestr);
                    pstm.addBatch();
                    j++;
                }
                pstm.executeBatch();
            }
            Long endTime = System.currentTimeMillis();
            System.out.println("用时：" + (endTime - startTime));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(pstm!=null){
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // 事务提交 + 批量插入
    // 需要加?rewriteBatchedStatements=true参数
    // 1000条数据，时间996毫秒，0.9秒
    // 100000,package = 24000,时间17838毫秒，17秒
    // 1000000,package = 24000,时间122789毫秒，122秒
    public void test04(){
        Connection conn = null;
        PreparedStatement pstm =null;
        ResultSet rt = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            float income_money = RandomUtil.getRandomBetweenFloat(0, 10000);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.of(1980, 1, 1);
            Long startTime = System.currentTimeMillis();
            String sql = "INSERT INTO `inspur_bss`.`contract_payment_complete_report_copy1`(`income_money`, `center_id`, `center_name`, `area_id`, `area_name`, `mu_id`, `mu_name`, `collection_date`, `service_line`, `created_time`, `updated_time`, `version`) " +
                    "VALUES (?, 2, '北京', 202006050015, '华北区域', 19, '北京', ?, 'cloudService', '2020-12-22 18:51:48', NULL, 1)";
            int packet = 24000;
            int size = 1000000;
            int j = 0;
            int n1 =  size / packet;
            int n2 =  size % packet;
            int num = (n2 == 0) ? n1 : n1+1;
            pstm = conn.prepareStatement(sql);
            for (int i = 0; i < num; i++) {
                conn.setAutoCommit(false);
                int packetPer = 0;
                if(n2 != 0 && i == num-1){
                    packetPer = n2;
                }else {
                    packetPer = packet;
                }
                // 每packet包大小，向mysql传输一次
                for (int k = 0; k < packetPer; k++) {
                    String datestr = date.plusDays(j).format(fmt);
                    pstm.setFloat(1, income_money);
                    pstm.setString(2, datestr);
                    pstm.addBatch();
                    j++;
                }
                pstm.executeBatch();
                conn.commit();
            }
            Long endTime = System.currentTimeMillis();
            System.out.println("用时：" + (endTime - startTime));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(pstm!=null){
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // 多线程-使用CompletableFuture，线程池使用ForkJoinPool.commonPool
    // 1000000,package = 24000,时间 119539毫秒，119秒
    public void test05(){
        Connection conn = null;
        final PreparedStatement[] pstm = {null};
        ResultSet rt = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            float income_money = RandomUtil.getRandomBetweenFloat(0, 10000);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.of(1980, 1, 1);
            Long startTime = System.currentTimeMillis();
            String sql = "INSERT INTO `inspur_bss`.`contract_payment_complete_report_copy1`(`income_money`, `center_id`, `center_name`, `area_id`, `area_name`, `mu_id`, `mu_name`, `collection_date`, `service_line`, `created_time`, `updated_time`, `version`) " +
                    "VALUES (?, 2, '北京', 202006050015, '华北区域', 19, '北京', ?, 'cloudService', '2020-12-22 18:51:48', NULL, 1)";
            int packet = 24000;
            int size = 1000000;
            final int[] j = {0};
            int n1 =  size / packet;
            int n2 =  size % packet;
            int num = (n2 == 0) ? n1 : n1+1;

            List<CompletableFuture> futureList = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                int packetPer = 0;
                if(n2 != 0 && i == num-1){
                    packetPer = n2;
                }else {
                    packetPer = packet;
                }
                Connection finalConn = conn;
                int finalPacketPer = packetPer;
                CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
                    PreparedStatement ps = null;
                    try {
                        ps = finalConn.prepareStatement(sql);
                        // 每packet包大小，向mysql传输一次
                        for (int k = 0; k < finalPacketPer; k++) {
                            String datestr = date.plusDays(j[0]).format(fmt);
                            ps.setFloat(1, income_money);
                            ps.setString(2, datestr);
                            ps.addBatch();
                            j[0]++;
                        }
                        ps.executeBatch();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        if (ps != null) {
                            try {
                                ps.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
                futureList.add(voidCompletableFuture);
            }
            // 阻塞全部
            CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()])).join();
            Long endTime = System.currentTimeMillis();
            System.out.println("用时：" + (endTime - startTime));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(pstm[0] !=null){
                try {
                    pstm[0].close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
