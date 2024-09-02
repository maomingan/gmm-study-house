package com.gmm.threadpool.pool;

import com.sun.tools.internal.ws.wsdl.document.soap.SOAPUse;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {

    public static void main(String[] args) {
//        showThreadCount();
        basicThreadPool();
//        execeptionThreadPool();
    }

    /**
     * 创建一个线程池，核心5最大10的线程池，运行后可以发现，线程池不会对于每个任务都new一个线程来执行，而是前面创建过得线程会被复用来处理后面的任务
     */
    public static void basicThreadPool(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 5000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));
        for(int i = 0; i < 20; i++){
            executor.submit(()->{
                System.out.println("I am the "+Thread.currentThread().getName());
            }, i);
        }
        // running状态 -> shutdown状态，已提交的任务会继续执行完成
        executor.shutdown();
        // running状态 -> stop，任务直接全部停止，不会等待一体镜的任务全部执行完成
        // executor.shutdownNow();
    }

    /**
     * 创建一个只有1个线程的线程池，并在任务里抛出异常，会发现，后面的任务依然会执行，不会因为异常而整个线程就不工作了
     */
    public static void execeptionThreadPool(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 5000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));
        for(int i = 0; i < 6; i++){
            executor.submit(()->{
                System.out.println("I am the "+Thread.currentThread().getName());
                throw new RuntimeException();
            });
        }
        executor.shutdown();
    }

    /**
     * Java的线程是KLT模型，而非ULT模型，说明我们每创建一个线程，就会在内核记录一次，即用户创建的线程数量跟内核记录的数量一致。
     * mac的话，可以使用top指令查看当前共有多少线程，然后执行下面的代码，会发现显示的线程数差不多增加了300个线程，服务停掉又会变少。
     */
    public static void showThreadCount(){
        for(int i = 0; i < 300; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

}
