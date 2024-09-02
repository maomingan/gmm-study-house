package com.gmm.threadconcurrent.tool.countdownlatch;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * 可以控制子线程都执行完成后，再执行主线程，比直接对线程join()的方式更加优雅
 */
@Slf4j
public class GoHomeTogether {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(2);
        new Thread(new BuyPillTask(latch),"张三").start();
        new Thread(new SeeDoctorTask(latch), "李四").start();
        latch.await();
        log.info("两个人都结束后，一起回家（子线程都执行完后，再执行主线程）！");

    }

    // 买药线程
    static class BuyPillTask implements Runnable{
        CountDownLatch latch;
        public BuyPillTask(CountDownLatch latch) {
            this.latch = latch;
        }
        @Override
        public void run() {
            String tName = Thread.currentThread().getName();
            try {
                log.info("{}去排队买药...", tName);
                Thread.sleep(2000);
                log.info("{}买完药了...", tName);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }
    // 看病
    static class SeeDoctorTask implements Runnable{
        CountDownLatch latch;
        public SeeDoctorTask(CountDownLatch latch) {
            this.latch = latch;
        }
        @Override
        public void run() {
            String tName = Thread.currentThread().getName();
            try {
                log.info("{}去排队看医生...", tName);
                Thread.sleep(5000);
                log.info("{}看完医生了...", tName);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }

}
