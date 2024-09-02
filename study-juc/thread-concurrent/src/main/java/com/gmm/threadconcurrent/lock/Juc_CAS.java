package com.gmm.threadconcurrent.lock;

import com.gmm.threadconcurrent.util.UnsafeInstance;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

@Slf4j
public class Juc_CAS {

    private volatile int state = 0;
    // 栅栏，可以指定多少个线程阻塞在它这里，然后再一起放行
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
    private static Juc_CAS cas = new Juc_CAS();

    public static void main(String[] args) {
        new Thread(new Worker(), "t-0").start();
        new Thread(new Worker(), "t-1").start();
        new Thread(new Worker(), "t-2").start();
        new Thread(new Worker(), "t-3").start();
        new Thread(new Worker(), "t-4").start();
    }

    static class Worker implements Runnable{
        @Override
        public void run() {
            String t_name = Thread.currentThread().getName();
            log.info("线程{}到达预定点，准备开始抢state...", t_name);
            try {
                cyclicBarrier.await();
                if(cas.compareAndSwapState(0, 1)){
                    log.info("线程{}抢到了锁!", t_name);
                }else{
                    log.info("线程{}抢锁失败！", t_name);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }


    private static final Unsafe unsafe = UnsafeInstance.reflectGetUnsafe();
    private static long stateOffset;
    static {
        try {
            stateOffset = unsafe.objectFieldOffset(Juc_CAS.class.getDeclaredField("state"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    /**
     * 原子操作
     * @param oldValue 线程工作内存当中的值
     * @param newValue 要替换的新值
     * @return
     */
    public final boolean compareAndSwapState(int oldValue, int newValue){
        return unsafe.compareAndSwapInt(this, stateOffset, oldValue, newValue);
    }

}
