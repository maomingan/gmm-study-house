package com.gmm.threadconcurrent.queue.blockingQueue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * 消费者
 */
@Slf4j
public class PillConsumer implements Runnable{

    private BlockingQueue<Integer> queue;

    public PillConsumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            eatPill();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 不断吃药
     */
    private void eatPill() throws InterruptedException {
        while (true){
            Integer pillNum = queue.take();
            log.info("{} 从药罐queue里取走了药，药罐当前药量：{}",Thread.currentThread().getName(),queue.size());
            Thread.sleep(5000);
        }
    }

}
