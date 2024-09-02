package com.gmm.threadconcurrent.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示ReentrantLock是一个可重入锁，且某个线程未完全释放锁的话，其它线程是无法获取锁去执行同步块的代码内容的。
 */
@Slf4j
public class Juc_ReentrantLock2 {

    private ReentrantLock lock = new ReentrantLock();

    public void reentrant() {

        String t_name = Thread.currentThread().getName();
        // 它是独占锁/排它锁，同一时刻只允许一个线程获取锁
        lock.lock();
        log.info("线程{}第1次加锁",t_name);

            lock.lock();
            log.info("线程{}第2次加锁",t_name);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lock.unlock();
            log.info("线程{}第1次解锁",t_name);

        lock.unlock();
        log.info("线程{}第2次解锁",t_name);

    }

    public static void main(String[] args) throws InterruptedException {

        Juc_ReentrantLock2 o = new Juc_ReentrantLock2();

        new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("t1启动了...");
                o.reentrant();
            }
        }, "t1").start();

        Thread.sleep(500);

        new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("t2启动了...");
                o.reentrant();
            }
        },"t2").start();

    }

}
