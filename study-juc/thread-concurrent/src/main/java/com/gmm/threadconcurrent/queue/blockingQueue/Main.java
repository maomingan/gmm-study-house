package com.gmm.threadconcurrent.queue.blockingQueue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 生产者生产的比较快，达到BlockingQueue的容量大小之后，就无法再生产入队，会阻塞在那，等待消费者先消费队列里的元素，不满的话才能继续生产
 */
@Slf4j
public class Main {

    public static void main(String[] args) {

        int queueMaxSize = 10;
        int producerNum = 16;
        int consumerNum = Runtime.getRuntime().availableProcessors(); // 运行环境的cpu核数

        producerNum = 2;
        consumerNum = 2;

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(queueMaxSize);

        // 生产者们开始制药
        for(int i = 1; i <= producerNum; i++){
            new Thread(new PillProducer(queue), "producer-"+i).start();
        }

        // 消费者开始拿药
        for(int i = 1; i <= consumerNum; i++){
            new Thread(new PillConsumer(queue),"consumer-"+i).start();
        }


    }

}
