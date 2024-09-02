package com.gmm.threadatomic.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题的解决办法，就是给共享数据加上版本号，数据每被操作一次，版本就+1，这样做CAS时，不但要比较共享数据的值是否一致，也要比较版本是否一致。都一致，才允许修改。
 * ABA问题是否需要解决，取决于业务场景，很多场景其实都不需要解决，只要不影响最后的结果就ok。
 */
@Slf4j
public class Atomic_ABAproblem_fix {

    // atomic包提供的带版本标识的类，stamp标识数据被修改次数
    static AtomicStampedReference<Integer> aiStampRef = new AtomicStampedReference<>(1, 0);

    public static void main(String[] args) {
        Thread main = new Thread(new Runnable() {
            @Override
            public void run() {
                String tName = Thread.currentThread().getName();
                int nowValue = aiStampRef.getReference();
                int nowStamp = aiStampRef.getStamp();
                log.info("{}初始查询，此时data：{}，stamp：{}",tName,nowValue,nowStamp);
                try {
                    // 休眠一段时间，让干扰线程进行干扰
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean b = aiStampRef.compareAndSet(nowValue, nowValue+10, nowStamp, nowStamp + 1);
                log.info("{}操作数据操作结果为{}，因为实际的值data：{}，stamp:{},版本值不一致.",tName,b,aiStampRef.getReference(),aiStampRef.getStamp());
            }
        },"[主线程]");

        Thread other = new Thread(new Runnable() {
            @Override
            public void run() {
                String tName = Thread.currentThread().getName();
                int stamp = aiStampRef.getStamp();
                aiStampRef.compareAndSet(1,2, stamp, stamp+1);
                log.info("{}改变数据，此时data：{}，stamp：{}",tName,aiStampRef.getReference(),aiStampRef.getStamp());
                stamp = aiStampRef.getStamp();
                aiStampRef.compareAndSet(2,1, stamp, stamp+1);
                log.info("{}恢复数据，此时data：{}，stamp：{}",tName,aiStampRef.getReference(),aiStampRef.getStamp());
            }
        },"[干扰线程]");

        main.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        other.start();

    }
}
