package com.gmm.threadatomic.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子类的方法，本身就是线程安全的，就不需要再自己去对它做同步操作
 */
public class Atomic_Integer {

    private static AtomicInteger ai = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(10);

        for(int i = 0; i < 10; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int k = 0; k < 1000; k++){
                        ai.getAndIncrement();
                    }
                    latch.countDown();
                }
            }).start();
        }

        latch.await();
        System.out.println("sum: "+ai.get());

    }

}
