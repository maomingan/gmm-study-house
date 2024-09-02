package com.gmm.threadconcurrent.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * ReentrantLock实现的3板斧：自旋、SupportLock、CAS
 * 其中SupportLock用于对线程的互斥阻塞以及唤醒，可以指定线程来唤醒。功能同wait和notify，但是notify是随机唤醒。
 */
@Slf4j
public class Juc_LockSupport {

    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String current = Thread.currentThread().getName();
                log.info("{}开始执行。。。", current);
                for(;;){ // 自旋
                    log.info("准备park住当前线程：{}", current);
                    LockSupport.park();
                    log.info("当前线程{}已被唤醒",current);
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String current = Thread.currentThread().getName();
                log.info("{}开始执行。。。", current);
                for(;;){ // 自旋
                    log.info("准备park住当前线程：{}", current);
                    LockSupport.park();
                    log.info("当前线程{}已被唤醒",current);
                }
            }
        });

        t1.start();
        t2.start();

        try {
            Thread.sleep(2000);
            log.info("main准备唤醒{}线程", t1.getName());
            LockSupport.unpark(t1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
