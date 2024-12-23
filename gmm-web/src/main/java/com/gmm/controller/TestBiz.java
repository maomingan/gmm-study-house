package com.gmm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @author Gmm
 * @date 2023/10/12
 */
@Service
@Slf4j
public class TestBiz {

    @Async("varThreadPool")
    public CompletableFuture<String> performAsyncTask(String taskName) {

        log.info(taskName + "start...");

        // 模拟一个耗时任务
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String result = taskName;
        try {
            double a = 1/0;
        }catch (Exception e){
            log.error("error: ", e);
            result = e.getMessage();
        }

        log.info(taskName + "finished...");

//        return CompletableFuture.completedFuture("Task '" + taskName + "' completed");
        return CompletableFuture.completedFuture(result);
    }

}
