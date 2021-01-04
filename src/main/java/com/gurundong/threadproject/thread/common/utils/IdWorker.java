package com.gurundong.threadproject.thread.common.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

/**
 * @author mengfanlong
 * <p>
 * snowflake算法<br>
 * <p>
 * SnowFlake的结构如下(每部分用-分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId<br>
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 * 加起来刚好64位，为一个Long型。<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 */
public class IdWorker {

    /**
     * 机器id所占的位数
     */
    private final long workerIdBits = 5L;

    /**
     * 数据标识id所占的位数
     */
    private final long datacenterIdBits = 5L;

    /**
     * 工作机器ID(0~31)
     */
    private long workerId;

    /**
     * 数据中心ID(0~31)
     */
    private long datacenterId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;
    private static IdWorker idWorker;

    static {
        idWorker = new IdWorker();
    }

    /**
     * 每次调用产生一个新的ID并返回
     *
     * @return 最新的ID
     */
    public static synchronized long getNextId() {
        return idWorker.nextId();
    }

    private IdWorker() {
        this.workerId = getWorkerId();
        this.datacenterId = getDatacenterId();
        //支持的最大机器id，31
        long maxWorkerId = -1L ^ (-1L << workerIdBits);
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        //支持的最大数据标识id，31
        long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
    }


    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    private synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //序列在id中占的位数
        long sequenceBits = 12L;
        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            //生成序列的掩码，4095 (0b111111111111=0xfff=4095)
            long sequenceMask = -1L ^ (-1L << sequenceBits);
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;


        //开始时间截 (2018-01-01 00:00:00)
        long twepoch = 1514736000000L;

        //数据标识id向左移17位(12+5)
        long datacenterIdShift = sequenceBits + workerIdBits;
        // 时间截向左移22位(5+5+12)
        long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
        return ((timestamp - twepoch) << timestampLeftShift)
                //移位并通过或运算拼到一起组成64位的ID
                | (datacenterId << datacenterIdShift)
                | (workerId << sequenceBits)
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 获取机器编码
     *
     * @return workerId
     */
    private long getWorkerId() {
        long machineId;
        StringBuilder sb = new StringBuilder();
        Enumeration<NetworkInterface> e = null;
        try {
            e = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e1) {
            e1.printStackTrace();
        }
        while (e != null && e.hasMoreElements()) {
            NetworkInterface ni = e.nextElement();

            Enumeration<InetAddress> addrs;
            addrs = ni.getInetAddresses();
            if (addrs == null) {
                continue;
            }

            // 获取IP地址(获取不到IP地址时使用网卡名)
            String ipStr = "";
            InetAddress ip;
            while (addrs.hasMoreElements()) {
                ip = addrs.nextElement();
                if (!ip.isLoopbackAddress() && ip.isSiteLocalAddress() && ip.getHostAddress().indexOf(":") == -1) {
                    try {
                        ipStr = ip.toString();
                    } catch (ArrayIndexOutOfBoundsException aioe) {
                        ipStr = ni.toString();
                    }
                }
            }
            sb.append(ipStr);
        }
        machineId = sb.toString().hashCode();
        //工作机器ID掩码
        long workerIdMask = -1L ^ (-1L << workerIdBits);
        return machineId & workerIdMask;
    }

    /**
     * 获取数据中心Id
     *
     * @return datacenterId
     */
    private long getDatacenterId() {
        //获取进程编码
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        long datacenterId = Long.valueOf(runtimeMXBean.getName().split("@")[0]);
        //数据中心ID掩码
        long datacenterIdMask = -1L ^ (-1L << datacenterIdBits);
        return datacenterId & datacenterIdMask;
    }

    public static String next() {
        return String.valueOf(getNextId());
    }

    /**
     * get uuid
     *
     * @return return id like 'c79454d6-9e0c-4b4a-a2bc-62f559f71570'
     * @since v0.2.22
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * get uuid
     *
     * @return return id like 'c79454d69e0c4b4aa2bc62f559f71570'
     * @since v0.2.22
     */
    public static String simpleUUID() {
        return uuid().replaceAll("-", "");
    }
}
