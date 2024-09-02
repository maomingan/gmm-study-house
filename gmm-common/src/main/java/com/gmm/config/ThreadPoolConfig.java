package com.gmm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Gmm
 * @date 2022/5/20
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    @Bean(name = "myTpe")
    public ThreadPoolExecutor myThreadPoolExecutor(){
        int cores = Runtime.getRuntime().availableProcessors();
        return new ThreadPoolExecutor(cores, 20, 60L, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(200), new ThreadPoolExecutor.AbortPolicy());
    }

    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数10：线程池创建时候初始化的线程数
        executor.setCorePoolSize(2);
        // 最大线程数20：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(10);
        // 缓冲队列200：用来缓冲执行任务的队列
        executor.setQueueCapacity(200);
        // 允许线程的空闲时间60秒：当超过了核心线程数之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);
        // 线程池名的前缀：设置好了之后可以方便定位处理任务所在的线程池
        executor.setThreadNamePrefix("async-executor-");
        /*
        线程池对拒绝任务的处理策略：这里采用了CallerRunsPolicy策略，
        当线程池没有处理能力的时候，该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；
        如果执行程序已关闭，则会丢弃该任务
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住。
        executor.setAwaitTerminationSeconds(600);
        return executor;
    }

    @Bean(value = "varThreadPool")
    public ThreadPoolTaskExecutor varThreadPool() {

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        //线程池维护线程的最少数量
        taskExecutor.setCorePoolSize(4);

        //线程池维护线程的最大数量
        taskExecutor.setMaxPoolSize(4);

        //允许的空闲时间
        taskExecutor.setKeepAliveSeconds(100);

        //缓存队列
        taskExecutor.setQueueCapacity(2);

        //线程池前缀名
        taskExecutor.setThreadNamePrefix("testExecutor---");

        //调度器shutdown被调用时等待当前被调度的任务完成
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        //等待时长
        taskExecutor.setAwaitTerminationSeconds(60);
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
        return taskExecutor;

    }

}
