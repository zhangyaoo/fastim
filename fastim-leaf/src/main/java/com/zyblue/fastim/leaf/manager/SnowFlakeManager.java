package com.zyblue.fastim.leaf.manager;

import com.zyblue.fastim.leaf.config.ZKConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 雪花算法SnowFlake
 * @author blueSky
 */
@Component
public class SnowFlakeManager {

    private final static long WORKER_ID_SHIFT = 6L;

    private final static long WORKER_ID_BITS = 16L;

    private final static long TIMESTAMP_LEFT_SHIFT = WORKER_ID_SHIFT + WORKER_ID_BITS;

    /**
     * 2020-10-01 00:00:00
     */
    private static final long START_TIME_STAMP = 1601481600000L;

    private static final short MAX_SEQUENCE = 63;

    /**
     * 上次时间
     */
    private long lastTimestamp = -1L;

    /**
     * 计数器
     */
    private  long  counter = 0L;

    /**
     * 机器ID
     */
    private  Long hostId;

    @Resource
    private ZKConfig zKConfig;

    /**
     * 获取本地的序列化
     */
    private  void initHost(){
        InetAddress localHostExactAddress = getLocalHostExactAddress();
        if(localHostExactAddress == null){
            hostId = 1L;
        }else {
            String hostAddress = localHostExactAddress.getHostAddress();
            String[] splits = hostAddress.split("\\.");
            if(splits.length == 4){
                long l = Long.parseLong(splits[2]);
                l = (l == 0 ? 1 : l) << 8;
                long l1 = Long.parseLong(splits[3]);
                l1 = (l1 == 0 ? 1 : l1);
                hostId = l | l1;
            }else {
                hostId = 1L;
            }
        }
    }

    public  synchronized Long nextLong(){
        Integer workerId = zKConfig.getWorkerId();
        if(workerId == null){
            return null;
        }

        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            long offset = lastTimestamp - timestamp;
            if (offset <= 5) {
                // 重新等待
                timestamp = getNextMillSecond();
            } else {
                throw new RuntimeException(String.format("lastTimestamp %s is after reference time %s", lastTimestamp, timestamp));
            }
        }
        if (lastTimestamp == timestamp) {
            if (counter < MAX_SEQUENCE) {
                counter++;
            } else {
                // 用完当前序列号，重新等待
                timestamp = getNextMillSecond();
                counter = 0L;
            }
        } else {
            counter = 0L;
        }
        lastTimestamp = timestamp;
        return ((timestamp - START_TIME_STAMP) << TIMESTAMP_LEFT_SHIFT) | hostId << WORKER_ID_SHIFT | counter;
    }

    private  long getNextMillSecond(){
        long currentTimeMillis = System.currentTimeMillis();
        while (currentTimeMillis <= lastTimestamp){
            currentTimeMillis = System.currentTimeMillis();
        }
        return currentTimeMillis;
    }

    private static InetAddress getLocalHostExactAddress() {
        try {
            InetAddress candidateAddress = null;

            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface iface = networkInterfaces.nextElement();
                // 该网卡接口下的ip会有多个，也需要一个个的遍历，找到自己所需要的
                for (Enumeration<InetAddress> inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = inetAddrs.nextElement();
                    // 排除loopback回环类型地址（不管是IPv4还是IPv6 只要是回环地址都会返回true）
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了 就是我们要找的
                            // ~~~~~~~~~~~~~绝大部分情况下都会在此处返回你的ip地址值~~~~~~~~~~~~~
                            return inetAddr;
                        }

                        // 若不是site-local地址 那就记录下该地址当作候选
                        if (candidateAddress == null) {
                            candidateAddress = inetAddr;
                        }

                    }
                }
            }

            // 如果出去loopback回环地之外无其它地址了，那就回退到原始方案吧
            return candidateAddress == null ? InetAddress.getLocalHost() : candidateAddress;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
