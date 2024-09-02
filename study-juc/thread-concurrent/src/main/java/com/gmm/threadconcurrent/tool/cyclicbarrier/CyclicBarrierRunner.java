package com.gmm.threadconcurrent.tool.cyclicbarrier;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟运动员跑步，可以模拟实现同一起跑线起跑执行的效果，且CyclicBarrier是可以使用来执行多次的
 */
@Slf4j
public class CyclicBarrierRunner {

    private static AtomicInteger rank = new AtomicInteger();

    static class Athlete implements Runnable{
        CyclicBarrier barrier;
        String athleteName;
        public Athlete(CyclicBarrier barrier, String name) {
            this.barrier = barrier;
            this.athleteName = name;
        }
        @Override
        public void run() {
//            String name = Thread.currentThread().getName();
            // 所有线程会阻塞在这里
            try {
                log.info("{}已经站在了起跑线上,准备就绪~",athleteName);
                barrier.await();
                // 模拟奔跑时间
                long runTime = ThreadLocalRandom.current().nextLong(9,100);
                Thread.sleep(runTime);
                // 到达终点
                log.info("{}结束比赛，获得了第{}名，用时{}s.", athleteName, rank.incrementAndGet(), runTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        CyclicBarrier barrier = new CyclicBarrier(10, ()->{
            log.info("所有运动员都已在起跑线准备就位，裁判明枪，比赛开始：");
        });

        log.info("第一场比赛准备开始啦！！！");
        // 模拟10个运动员
        for(int i = 1; i <= 10; i ++){
            new Thread(new Athlete(barrier, "运动员-"+i)).start();
        }

        Thread.sleep(5000);
        log.info("第二场比赛准备开始啦！！！");
        for(int i = 101; i <= 110; i ++){
            new Thread(new Athlete(barrier, "运动员-"+i)).start();
        }

    }

}
