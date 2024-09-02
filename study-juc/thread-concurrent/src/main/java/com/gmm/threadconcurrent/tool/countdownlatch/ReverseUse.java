package com.gmm.threadconcurrent.tool.countdownlatch;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 反着使用，可以用来模拟更加精确的并发，把所有线程先创建好，然后都阻塞同一起跑线，然后再调用countDown()发抢起跑。
 * 先调用await阻塞，再调用countDown发抢。效果同使用CyclicBarrier，只是这里的counterDownLatch只能使用一次。
 */
@Slf4j
public class ReverseUse {

    public static void main(String[] args) throws InterruptedException {

        // 模拟跑步比赛，统一起跑
        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger rank = new AtomicInteger();

        for(int i = 1; i <= 10; i++){
            new Thread(()->{
                String name = Thread.currentThread().getName();
                // 所有线程会阻塞在这里
                try {
                    log.info("{}已经站在了起跑线上,准备就绪~",name);
                    latch.await();
                    // 模拟奔跑时间
                    long runTime = ThreadLocalRandom.current().nextLong(9,100);
                    Thread.sleep(runTime);
                    // 到达终点
                    log.info("{}结束比赛，获得了第{}名，用时{}s.", name, rank.incrementAndGet(), runTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }, "运动员-"+i).start();
        }

        // 主线程模拟裁判发枪
        Thread.sleep(3000);
        log.info("主裁判发枪，跑步比赛开始！");
        latch.countDown();

    }

}
