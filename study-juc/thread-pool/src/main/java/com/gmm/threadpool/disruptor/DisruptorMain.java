package com.gmm.threadpool.disruptor;

import com.gmm.threadpool.disruptor.event.LongEvent;
import com.gmm.threadpool.disruptor.event.LongEventFactory;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DisruptorMain {

    public static void main(String[] args) {

        // 1.创建一个可缓存的线程池，提供线程给Consumer做事件处理
        ExecutorService es = Executors.newCachedThreadPool();
        // 2.创建工厂
        EventFactory<LongEvent> factory = new LongEventFactory();
        // 3.创建ringbuffer大小 值一定要是2的N次方，因为底层采用位运算
        int ringBufferSize = 1024;
        // 4.创建Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, ringBufferSize, es,
                ProducerType.SINGLE, new YieldingWaitStrategy());
        // 5.连接消费端方法
        disruptor.handleEventsWith(new LongEventHandler(1), new LongEventHandler(2));
        // 6.启动disruptor
        disruptor.start();
        // 7.创建RingBuffer容器
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        // 8.创建生产者
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        // 9.指定缓冲区大小
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        // 10.模拟生产消费数据
        for (int i = 1; i <= 100; i++) {
            byteBuffer.putLong(0, i);
            producer.onData(byteBuffer);
        }
        // 11.关闭disruptor和线程池
        disruptor.shutdown();
        es.shutdown();

    }

}
