package com.gmm.rate_limiter;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * 限流策略2：漏桶算法
 * @author Gmm
 * @date 2024/11/1
 */
@Data
@RequiredArgsConstructor
@Slf4j
public class LeakyBucket {

    // 漏桶流水速率，每秒处理多少桶里的水滴(请求)
    @NonNull
    private int rate;
    // 漏桶大小，最多可以装多少水滴(请求)
    @NonNull
    private int size;
    // 最后更新时间，最新的水滴入桶时间
    private int refreshTime;
    // 漏桶内的水量
    private int water;

    /**
     * 是否被限流
     * @return true则被限流，false则请求放通行
     */
    public synchronized boolean limited(){
        if(water == 0){
            // 如果是空桶
            refreshTime = LocalDateTime.now().getSecond();
            water += 1;
            return false;
        }
        // 执行漏水
        int leakCnt = (LocalDateTime.now().getSecond() - refreshTime) * rate;
        // 计算剩余水量
        int leftWater = water - leakCnt;
        water = Math.max(0, leftWater);
        // 更新最后更新时间
        refreshTime = LocalDateTime.now().getSecond();
        // 如果桶未满，则水滴可以入桶,放行
        if(water < size){
            water += 1;
            return false;
        }else{
            // 水满，拒绝加上，限流
            return true;
        }
    }

    public static void main(String[] args) {

        // 构建一个大小为5，处理速率为每秒处理1个水滴(请求)的漏桶
        LeakyBucket leakyBucket = new LeakyBucket(1, 5);

        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(random.nextInt(200));
//                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(() -> {
                log.info(Thread.currentThread().getName() + "请求是否被限流：" + leakyBucket.limited());
            }).start();
        }

    }


}
