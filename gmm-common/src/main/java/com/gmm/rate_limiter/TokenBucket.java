package com.gmm.rate_limiter;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;

/**
 * 限流策略3：令牌桶
 * @author Gmm
 * @date 2024/11/1
 */
@Slf4j
public class TokenBucket {

    public static void main(String[] args) {

        // RateLimiter封装了令牌桶算法，这里每秒生成1个令牌
        final RateLimiter rateLimiter = RateLimiter.create(1);

        new Thread(() -> {
            // 某个请求一次性获取5个令牌，直接从桶内获取到令牌，所以无需等待
            log.info("{}获取5个令牌等待时间：{}", Thread.currentThread().getName(), rateLimiter.acquire(5));
        }).start();

        new Thread(() -> {
            // 某个请求一次性获取2个令牌，因为前面有现成获取5个令牌，所以这里阻塞等待5s
            log.info("{}获取2个令牌等待时间：{}", Thread.currentThread().getName(), rateLimiter.acquire(2));
        }).start();

        new Thread(() -> {
            // 某个请求一次性获取1个令牌
            log.info("{}获取1个令牌等待时间：{}", Thread.currentThread().getName(), rateLimiter.acquire(1));
        }).start();

    }

}
