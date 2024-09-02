package com.gmm.threadconcurrent.queue.blockingQueue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 生产者
 */
@Slf4j
public class PillProducer implements Runnable{

    private BlockingQueue<Integer> queue;

    public PillProducer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            makePill();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 不断制药
     * @throws InterruptedException
     */
    private void makePill() throws InterruptedException {
        // 高并发的环境下，使用ThreadLocalRandom.current()来生成随机数比直接使用共享的Random对象来生成效率要高
        while (true){
            int pillNum = ThreadLocalRandom.current().nextInt(20);
            queue.put(pillNum);
            log.info("{} 往药罐queue里生产了药片, 药罐当前药量：{}",Thread.currentThread().getName(),queue.size());
            if(queue.remainingCapacity() == 0){
                log.info("药罐已满，生产者阻塞，等待消费者先消费一些");
            }
            Thread.sleep(2000);
        }
    }

}
