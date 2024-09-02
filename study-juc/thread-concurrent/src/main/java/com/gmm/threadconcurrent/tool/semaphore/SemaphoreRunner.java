package com.gmm.threadconcurrent.tool.semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Semaphore用于控制同时允许几个线程同时执行，可用于做限流
 */
@Slf4j
public class SemaphoreRunner {

    private static volatile List<String> list = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        Semaphore semaphore = new Semaphore(2);
        for(int i = 1; i <= 10; i++){
            new Thread(new Task(semaphore), "task-"+i).start();
            Thread.sleep(1000);
        }

    }

    static class Task implements Runnable{

        private Semaphore semaphore;

        public Task(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            String tName = Thread.currentThread().getName();
            try {
                // 获取访问公共资源的锁
                semaphore.acquire();
                // 业务逻辑
                list.add(tName);
                log.info("{}于{}获取到锁，开始处理业务逻辑.当前正在运行的作业有：{}", tName, System.currentTimeMillis(), list);
                Thread.sleep(ThreadLocalRandom.current().nextInt(5)*1000);
                // 释放锁
                list.remove(tName);
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
