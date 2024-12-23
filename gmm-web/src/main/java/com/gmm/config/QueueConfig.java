package com.gmm.config;

import com.gmm.workcase.async.AsyncTask;
import com.gmm.workcase.async.TaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author Gmm
 * @date 2022/7/7
 */
@Configuration
@Slf4j
public class QueueConfig {

    @Bean
    public BlockingQueue<TaskInfo> globalBlockingQueue(){
        log.info("globalBlockingQueue初始化...");
        BlockingQueue<TaskInfo> queue = new LinkedBlockingDeque<>();
        return queue;
    }

}
