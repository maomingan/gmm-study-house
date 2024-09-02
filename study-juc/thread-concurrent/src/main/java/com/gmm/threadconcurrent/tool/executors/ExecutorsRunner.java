package com.gmm.threadconcurrent.tool.executors;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Executors这个工具类，现在一般没人使用，阿里规范要求不使用它来创建线程池，一定要手动创建线程池
 */
@Slf4j
public class ExecutorsRunner {

    public static void main(String[] args) {

        int threadNum = 5;
        AtomicInteger ai = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);

        for(int i = 1; i <= 5; i++){
            executorService.execute(()->{
                log.info("{}开始加1000", Thread.currentThread().getName());
                for(int k = 0; k < 1000; k++ ){
                    ai.getAndAdd(2);
                }
            });
        }

        //关闭线程池,只有先关闭线程池，才能等正在执行的线程执行完成后，使用isterminated来判断是否全部执行完毕
        executorService.shutdown();
        while (true){
            if(executorService.isTerminated()){
                log.info("sum: "+ai.get());
                break;
            }
        }
        System.exit(-1);

    }

}
