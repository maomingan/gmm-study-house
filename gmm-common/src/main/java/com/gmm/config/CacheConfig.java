package com.gmm.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author Gmm
 * @date 2022/5/31
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public Cache<String, Object> asyncTaskCache(){
        return Caffeine.newBuilder()
                // 初始的缓存空间大小
                .initialCapacity(10)
                // 缓存的最大条数
                .maximumSize(1000)
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build();
    }

    @Bean
    public Cache<String, Object> threadCache(){
        return Caffeine.newBuilder()
                // 初始的缓存空间大小
                .initialCapacity(10)
                // 缓存的最大条数
                .maximumSize(1000)
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build();
    }

}
