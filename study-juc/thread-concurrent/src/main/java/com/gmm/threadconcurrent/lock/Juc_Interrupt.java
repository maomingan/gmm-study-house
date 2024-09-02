package com.gmm.threadconcurrent.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程的interrupt可以用于给被阻塞的线程加上中断信号，并唤醒被阻塞的线程，然后线程可根据中断信号标识去做一些处理
 */
@Slf4j
public class Juc_Interrupt {

    public static void main(String[] args) throws InterruptedException {

        demo1();
//        demo2();
//        demo3();

    }

    /**
     * 唤醒被LockSupport.park()阻塞的线程，有2种方法：
     *      1、使用LockSupport.unpark(t),这种方法只会唤醒一次
     *      2、使用t.interrupt()打中断信号的方法，这种方法会使得t线程里的阻塞完全被放开，不再具有阻塞作用。
     */
    public static void demo1() throws InterruptedException{

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String t_name = Thread.currentThread().getName();
                for(;;){
                    log.info("线程{}准备阻塞...", t_name);
                    // 阻塞线程
                    LockSupport.park();
                    log.info("线程{}被唤醒了!", t_name);
                }
            }
        });
        t.start();

        Thread.sleep(2000);
        // 主线程给t这个线程打上中断信号
        t.interrupt();
    }

    /**
     * 使用打上中断信号来进行唤醒的方式，可以柔性的使用线程的isInterrupted()方法来判断并进行更细粒度的中断控制
     * @throws InterruptedException
     */
    public static void demo2() throws InterruptedException{
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread current = Thread.currentThread();
                String t_name = current.getName();
                for(;;){
                    // 获取中断信号，获取到就可以做一些别的操作，比如不执行别的业务代码等
                    if(current.isInterrupted()){
                        break;
                    }
                    // 下面假设都是业务逻辑
                    log.info("线程{}准备阻塞...", t_name);
                    // 阻塞线程
                    LockSupport.park();
                    log.info("线程{}被唤醒了!", t_name);
                }
                log.info("线程{}获取到中断信号量之后退出了");
            }
        });
        t.start();

        Thread.sleep(2000);
        // 先唤醒，但是此时是拿不到中断信号的，会继续循环执行
        LockSupport.unpark(t);
        Thread.sleep(2000);
        // 主线程给t这个线程打上中断信号，为了中断这个线程，不让这个线程继续做业务逻辑
        t.interrupt();
    }

    /**
     * 同demo1，只是这里阻塞使用的是更高级的ReentrantLock.lock方法来实现阻塞
     * @throws InterruptedException
     */
    public static void demo3() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(true);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread current = Thread.currentThread();
                String t_name = current.getName();
                for(;;){
                    log.info("线程{}准备阻塞...", t_name);
                    // 阻塞线程
                    lock.lock();
                    log.info("线程{}被唤醒了!", t_name);
                }
            }
        });
        t.start();

        Thread.sleep(2000);
        // 主线程给t这个线程打上中断信号，为了中断这个线程，不让这个线程继续做业务逻辑
        t.interrupt();
    }

}
