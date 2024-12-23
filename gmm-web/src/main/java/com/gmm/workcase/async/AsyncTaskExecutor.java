package com.gmm.workcase.async;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Gmm
 * @date 2022/5/24
 */
@Component
@Slf4j
public class AsyncTaskExecutor {

    @Autowired
    ThreadPoolExecutor myTpe;

    @Autowired
    Cache threadCache;

    @Async("asyncExecutor")
//    @Transactional(rollbackFor = Exception.class)
    public void execute(AsyncTask task, String taskID) {
        Thread curThread = Thread.currentThread();
        threadCache.put(taskID, curThread);
        log.info("prepare execute async task：{}, thread is: {}", taskID, Thread.currentThread().getName());
        try {
            task.async();
        } catch (InterruptedException e) {
            log.error(" {} interrupted. e: {}", curThread.getName(), e);
            throw new RuntimeException(curThread.getName()+" interrupted.");
        }
    }

//    public void executeRunnable(Runnable task, String taskID){
//        log.info("before executing task:{}",taskID);
//        myTpe.execute(task);
//        log.info("after executing task:{}",taskID);
//    }

    public void interrupt(String taskID){
        Thread t = (Thread) threadCache.getIfPresent(taskID);
        t.interrupt();
    }

}
