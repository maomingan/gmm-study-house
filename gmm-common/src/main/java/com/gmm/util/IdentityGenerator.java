package com.gmm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 雪花算法获取唯一键
 * @author Gmm
 * @date 2024/9/9
 */
public class IdentityGenerator {

    private static final Logger logger = LoggerFactory.getLogger(IdentityGenerator.class);

    /**
     * 起始的时间戳, 2010-01-01 00:00:00, 定好后不能轻易更改, 否则可能会出现重复
     */
    private final static long START_MILLIS = 1262275200000L;
    /**
     * 序列号占用的位数
     */
    private final static long SEQUENCE_BIT = 12;
    /**
     * 机器标识占用的位数
     */
    private final static long NODE_BIT = 5;
    /**
     * 数据中心占用的位数
     */
    private final static long DC_BIT = 5;
    /**
     * 每一部分最大值
     */
    private final static long DC_MAX = ~(-1L << DC_BIT);
    private final static long NODE_MAX = ~(-1L << NODE_BIT);
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
    /**
     * 每一部分向左的位移
     */
    private final static long NODE_LEFT = SEQUENCE_BIT;
    private final static long DC_LEFT = SEQUENCE_BIT + NODE_BIT;
    private final static long TIMESTMP_LEFT = DC_LEFT + DC_BIT;

    /**
     * 数据中心
     */
    private long dcId;
    /**
     * 机器标识
     */
    private long nodeId;
    /**
     * 序列号
     */
    private volatile long sequence = 0L;
    /**
     * 上一次时间戳
     */
    private volatile long lastMillis = -1L;
    private volatile long lastId = 0L;

    private static final ConcurrentHashMap<ServiceTypeEnum, IdentityGenerator> GENERATORS = new ConcurrentHashMap<>();

    private IdentityGenerator(ServiceTypeEnum serviceType) {
        // 使用机器IP的最后的数字作为nodeId
        this(serviceType.getCode(), IPUtils.addressTail());
    }

    private IdentityGenerator(long dcId, long nodeId) {
        this.dcId = dcId & DC_MAX;
        this.nodeId = nodeId & NODE_MAX;
    }

    /**
     * 使用默认的Id生成
     */
    public static long nextId() {
        return nextId(ServiceTypeEnum.DEFAULT);
    }

    /**
     * 根据不同服务生成Id
     */
    public static long nextId(ServiceTypeEnum serviceType) {
        if (null == serviceType) {
            serviceType = ServiceTypeEnum.DEFAULT;
        }

        IdentityGenerator identityGenerator = GENERATORS.get(serviceType);
        if (null == identityGenerator) {
            synchronized (IdentityGenerator.class) {
                identityGenerator = GENERATORS.get(serviceType);
                if (null == identityGenerator) {
                    logger.info("初始化 {} 服务的IdentityGenerator......", serviceType);
                    identityGenerator = new IdentityGenerator(serviceType);
                    GENERATORS.put(serviceType, identityGenerator);
                }
            }
        }

        return identityGenerator.generator();
    }

    /**
     * 产生下一个Id
     *
     * @return
     */
    private synchronized long generator() {
        long currentMillis = System.currentTimeMillis();
        if (currentMillis < lastMillis) {
//            throw new RuntimeException("Clock moved backwards " + (lastMillis - currentMillis) + "ms. Refusing to generate id");
            // 时钟回拨, 最后生成的lastId + 1
            return ++lastId;
        }

        if (currentMillis == lastMillis) {
            // 当前调用和上一次调用落在了相同毫秒内，只能通过第三部分，序列号自增来判断为唯一，所以+1.
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒的序列数已经达到最大，只能等待下一个毫秒
            if (sequence == 0L) {
                currentMillis = getNextMillis();
            }
        } else {
            // 不同毫秒内，序列号置为0
            // 前提是currentMillis > lastMillis，说明本次调用跟上次调用对比，已经不再同一个毫秒内了，这个时候序号可以重新回置0了。
            sequence = 0L;
        }

        lastMillis = currentMillis;
        long nextId = (currentMillis - START_MILLIS) << TIMESTMP_LEFT
                | dcId << DC_LEFT
                | nodeId << NODE_LEFT
                | sequence;

        if (nextId <= lastId) {
            nextId = ++lastId;
        } else {
            lastId = nextId;
        }

        return nextId;
    }

    private long getNextMillis() {
        long nextMillis = System.currentTimeMillis();
        while (nextMillis <= lastMillis) {
            nextMillis = System.currentTimeMillis();
        }

        return nextMillis;
    }

    public static void main(String[] args) {
        System.out.println(nextId());
    }

}
