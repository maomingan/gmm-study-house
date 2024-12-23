package com.gmm.controller;

import com.github.benmanes.caffeine.cache.Cache;
import com.gmm.workcase.async.TaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

/**
 * @author Gmm
 * @date 2022/5/20
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    Cache threadCache;
    @Autowired
    BlockingQueue<TaskInfo> globalBlockingQueue;

    @Autowired
    ThreadPoolTaskExecutor varThreadPool;

    @Autowired
    TestBiz testBiz;

    @RequestMapping("/hi")
    public String hi(){
        return "hi, gmm!";
    }

    @RequestMapping("/testWait")
    public String testWait(){
        final Object lock = new Object();
        synchronized (lock){
            log.info("开始执行业务方法。。。");
            TaskInfo taskInfo = TaskInfo.builder()
                    .task_id(String.valueOf(UUID.randomUUID()))
                    .task_name("测试wait和notify")
                    .input("input data")
                    .ext("binning")
                    .build();
            threadCache.put(taskInfo.getTask_id(), lock);
            globalBlockingQueue.add(taskInfo);
            log.info("血缘任务1入队完成："+taskInfo.getTask_id());
            try {
                lock.wait(1000*60);
                log.info("用户线程被唤醒，继续执行同步业务逻辑...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            taskInfo.setExt("modeling");
            globalBlockingQueue.add(taskInfo);
            log.info("血缘任务1入队完成："+taskInfo.getTask_id());
            try {
                lock.wait(1000*60);
                log.info("用户线程被唤醒，继续执行同步业务逻辑...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return taskInfo.toString();
        }
    }

    @RequestMapping("/testNotify")
    public String testNotify(){
        TaskInfo taskInfo = null;
        try {
            taskInfo = globalBlockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("取出队列任务："+taskInfo.getTask_id());
        taskInfo.setOutput("output data");
        Object lock = threadCache.getIfPresent(taskInfo.getTask_id());
        synchronized (lock){
            lock.notify();
        }
        return "执行队列任务和唤醒用户线程完成:"+taskInfo;
    }

    @RequestMapping("/testBatch")
    public List<String> testBatch(){

        List<CompletableFuture<String>> futures = new ArrayList<>();

        for (int i=0; i<100; i++){
            CompletableFuture<String> future = testBiz.performAsyncTask("gmmtask" + i);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));

        List<String> execResult = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        log.info("所有执行完毕...");

        return execResult;
    }

}
