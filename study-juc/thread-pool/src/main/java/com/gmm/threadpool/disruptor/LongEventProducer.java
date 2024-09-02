package com.gmm.threadpool.disruptor;

import com.gmm.threadpool.disruptor.event.LongEvent;
import com.lmax.disruptor.RingBuffer;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

@Slf4j
public class LongEventProducer {

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer byteBuffer){
        // 1.获取ringbuffer的下一个自增值
        long sequence = ringBuffer.next();
        Long data = null;
        try {
            // 2.取出对应的空的环形队列槽位对象
            LongEvent longEvent = ringBuffer.get(sequence);
            // 3.获取事件队列传递的数据
            data = byteBuffer.getLong(0);
            longEvent.setValue(data);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
            log.info("生产者发送数据到环形事件队列里...");
            // 4. 发布事件
            ringBuffer.publish(sequence);
        }

    }

}
