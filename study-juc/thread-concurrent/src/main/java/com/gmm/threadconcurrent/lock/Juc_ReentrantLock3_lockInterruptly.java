package com.gmm.threadconcurrent.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock的lockInterruptibly进行线程阻塞的话，t1唤醒但是却拿不到锁的话，就可以抛出异常;
 * 如果是使用lock()方法的话，则不会抛出异常。
 */
@Slf4j
public class Juc_ReentrantLock3_lockInterruptly {

    private static ReentrantLock lock = new ReentrantLock(true);

    public static void reentrantLock() throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        boolean flag = false;
        lock.lockInterruptibly();
            log.info("Thread:{},加锁成功!",threadName);
            while(true){
                if(Thread.interrupted()){
                    break;
                }
                log.info("线程{}在执行中...", threadName);
                Thread.sleep(100);
                //逻辑,批处理数据
                //逻辑
            }
        lock.unlock();
        log.info("Thread:{},锁退出同步块",threadName);
    }

    public static void main(String[] args) {
        Thread t0 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    reentrantLock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t0");
        t0.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    reentrantLock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //异常处理
                }
            }
        },"t1");
        t1.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t0.interrupt();

    }

}
