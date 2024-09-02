package com.gmm.threadconcurrent.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock可以实现synchronized同样的功能，且更加灵活，可以自己控制加锁和解锁的位置，且也是可重入锁
 */
public class Juc_ReentrantLock {

    // true为公平锁
    private static ReentrantLock lock = new ReentrantLock(true);
    private static volatile int counter = 0;
    // 用于优雅的实现主线程等待所有的子线程执行完毕，直到减到0则await不再阻塞
    private static CountDownLatch countDownLatch = new CountDownLatch(10);

    public static void main(String[] args) throws Exception{

        for(int i = 1; i <= 10; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++){
                        // 等同于 synchronized (obj) 的同步块
                        lock.lock();
                        counter++;
                        lock.unlock();
                    }
                    countDownLatch.countDown();
                }
            }).start();
        }

        countDownLatch.await();
        System.out.println(counter);

    }

}
