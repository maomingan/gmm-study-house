package com.gmm.threadpool.schedule;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 延时/定时执行线程池
 */
@Slf4j
public class ScheduleThreadPoolDemo {

    public static void main(String[] args) throws Exception{
//        demo01();
//        demo02();
        demo03();
    }

    /**
     * 定时器线程池
     * 可以获取返回值，通过future.get()获取，获取到之前会一直阻塞在这个地方，获取到之后才会继续往后执行
     */
    public static void demo01() throws ExecutionException, InterruptedException {
        ScheduledThreadPoolExecutor scheduleExecutor = new ScheduledThreadPoolExecutor(1);
        ScheduledFuture<?> future = scheduleExecutor.schedule(() -> {
            System.out.println("延迟3s执行");
            return "阿甘";
        }, 3, TimeUnit.SECONDS);
        System.out.println("主线程执行到这了");
        System.out.println("future返回对象值："+future.get());
        System.out.println("future.get()阻塞完成了，主线程继续执行结束");
        scheduleExecutor.shutdown();
    }

    /**
     * 任务1：scheduleAtFixedRate是固定间隔时间执行一次任务（隔2s）,一直用的同一个线程
     * 任务2：如果任务时间过长，任务结束后会马上执行下一次定时任务（隔5s），而不会任务没执行结束的时候，就又new一个线程出来执行下一次定时任务
     * 任务3：任务里如果抛出异常，则该任务终止，但是线程池的其它任务不会受到任何影响.所以，在定时器线程池的任务当中，一定要自己捕获异常，否则任务会终止影响业务
     * 任务4：scheduleWithFixedDelay是任务完成之后再固定间隔一段时间（隔7s），再执行下一次任务
     */
    public static void demo02(){
        ScheduledThreadPoolExecutor scheduleExecutor = new ScheduledThreadPoolExecutor(4);
        // 任务1
        scheduleExecutor.scheduleAtFixedRate(()->{
            log.info("固定频率执行任务1");
        },1000, 2000, TimeUnit.MILLISECONDS);
        // 任务2
        scheduleExecutor.scheduleAtFixedRate(()->{
            // 模拟需要执行的任务时间过程，这里用5s
            try {
                log.info("固定频率执行任务2");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },1, 2, TimeUnit.SECONDS);
        // 任务3
        scheduleExecutor.scheduleAtFixedRate(()->{
            log.info("固定频率执行任务3");
            throw new RuntimeException("不期望出现异常，任务3停止");
        },1, 2, TimeUnit.SECONDS);
        // 任务4
        scheduleExecutor.scheduleWithFixedDelay(()->{
            try {
                log.info("固定频率执行任务4");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },1, 2, TimeUnit.SECONDS);

    }

    /**
     * JDK自带的工具Timer有相同的定时/延时任务功能，当我们做一个小任务不想引入其它第三方包的时候，就直接用它就行
     * 但是注意：timer如果遇到异常，那么这个timer就退出了，等于timer相关的任务都终止了，所以一定要捕获异常。而定时线程池只是发生异常的任务停止，不会影响其它。
     */
    public static void demo03(){
        Timer timer = new Timer();
        // 任务1
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("执行任务1");
            }
        }, 1000, 2000);
        // 任务2
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                log.info("执行任务2");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },1000,2000);
        // 任务3
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                log.info("执行任务3");
                throw new RuntimeException("error");
            }
        },1000,2000);

    }

}
