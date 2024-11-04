package com.gmm.rate_limiter;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 限流策略1：滑动窗口（计数器升级版）
 * @author Gmm
 * @date 2024/11/4
 */
@Slf4j
public class SlideWindow {

    /**
     * 队列ID和队列的映射，这样可以使得程序里允许有多个限流队列
     */
    private volatile static Map<String, List<Integer>> MAP = new ConcurrentHashMap<>();

    /**
     * 判断是否需要限流
     * @param listId 队列id
     * @param limitCnt 限制请求数
     * @param timeWindow 窗口大小（秒）
     * @return true：限流 false：允许通过
     */
    public static synchronized boolean limited(String listId, int limitCnt, int timeWindow){
        // 根据id取出限流队列，如果没有则新创建
        final List<Integer> list = MAP.computeIfAbsent(listId, key -> new LinkedList<>());
        // 获取当前秒级时间
        final int now = LocalDateTime.now().getSecond();
        // 如果队列未满，则放行，然后把当前时间放入队列头
        if (list.size() < limitCnt) {
            list.add(0, now);
            return false;
        }
        // 队列已满，则取出队列最早时间（即队尾时间）
        final Integer earliestTime = list.get(limitCnt - 1);
        // 如果当前时间减去最早时间，还在窗口时间里，则限流
        if ((now - earliestTime) <= timeWindow) {
            return true;
        }else{
            // 超过窗口时间，放行，并处理队列
            list.remove(limitCnt - 1);
            list.add(0, now);
            return false;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Random r = new Random();
        while (true) {
            // 1s内只允许通过2个请求，其它会被限流
            log.info("是否被限流：{}", SlideWindow.limited("ListId", 2, 1));
            Thread.sleep(r.nextInt(200));
        }
    }

}
