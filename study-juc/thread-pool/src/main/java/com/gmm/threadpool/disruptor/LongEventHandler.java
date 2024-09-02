package com.gmm.threadpool.disruptor;

import com.gmm.threadpool.disruptor.event.LongEvent;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LongEventHandler implements EventHandler<LongEvent> {

    private long ser = 0;

    public LongEventHandler(long ser) {
        this.ser = ser;
    }

    @Override
    public void onEvent(LongEvent longEvent, long sequence, boolean endOfBatch) throws Exception {
        log.info("消费者-{}去消费了:{}", ser, longEvent.getValue());
    }
}
