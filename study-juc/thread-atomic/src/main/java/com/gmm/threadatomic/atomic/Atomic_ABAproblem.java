package com.gmm.threadatomic.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS会产生的一个问题就是ABA问题，即1个线程去查询（开始是A值）和修改共享数据的中间过程，被其它线程把数据修改成B，然后再改回成A。
 * 这样这个线程修改的时候，compare还是返回true，然后就成功的进行了修改。但是殊不知，中间其实已经被其它线程修改过了。
 *
 * ABA问题的解决办法，就是给共享数据加上版本号，数据每被操作一次，版本就+1，这样做CAS时，不但要比较共享数据的值是否一致，也要比较版本是否一致。都一致，才允许修改。
 * ABA问题是否需要解决，取决于业务场景，很多场景其实都不需要解决，只要不影响最后的结果就ok。
 */
@Slf4j
public class Atomic_ABAproblem {
    static AtomicInteger atomicInteger = new AtomicInteger(1);

    public static void main(String[] args) {
        Thread main = new Thread(new Runnable() {
            @Override
            public void run() {
                int a = atomicInteger.get();
                log.info("操作线程"+Thread.currentThread().getName()+"--修改前操作数值:"+a);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean isCasSuccess = atomicInteger.compareAndSet(a,2);
                if(isCasSuccess){
                    log.info("操作线程"+Thread.currentThread().getName()+"--Cas修改后操作数值:"+atomicInteger.get());
                }else{
                    log.info("CAS修改失败");
                }
            }
        },"[主线程]");

        Thread other = new Thread(new Runnable() {
            @Override
            public void run() {
                atomicInteger.incrementAndGet();// 1+1 = 2;
                log.info("操作线程"+Thread.currentThread().getName()+"--increase后值:"+atomicInteger.get());
                atomicInteger.decrementAndGet();// atomic-1 = 2-1;
                log.info("操作线程"+Thread.currentThread().getName()+"--decrease后值:"+atomicInteger.get());
            }
        },"[干扰线程]");

        main.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        other.start();

    }
}
