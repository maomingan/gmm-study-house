package com.gmm.threadconcurrent.queue.delayedQueue;

import lombok.Getter;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列
 */
@Getter
public class MovieTicket implements Delayed {

    // 票名
    private final String ticktName;
    // 延迟时间
    private final long delay;
    // 过期时间
    private final long expire;

    public MovieTicket(String ticktName, long delay) {
        this.ticktName = ticktName;
        this.delay = delay;
        this.expire = System.currentTimeMillis() + delay;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long duration = this.expire - System.currentTimeMillis();
        long delay = unit.convert(duration, TimeUnit.MILLISECONDS);
        return delay;
    }

    @Override
    public int compareTo(Delayed o) {
        return (int)(this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public String toString() {
        return "MovieTicket{" +
                "ticktName='" + ticktName + '\'' +
                ", delay='" + delay + '\'' +
                '}';
    }

}
