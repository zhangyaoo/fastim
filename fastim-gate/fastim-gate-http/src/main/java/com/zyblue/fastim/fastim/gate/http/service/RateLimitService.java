package com.zyblue.fastim.fastim.gate.http.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author will
 * @date 2021/6/29 11:11
 */
@Component
public class RateLimitService {

    @Value("${request.rate.minutes.limit}")
    private Integer limit;

    /**
     * 请求的缓存
     * key：用户ID
     * value：计数器
     */
    private final Cache<String, Integer> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(limit, TimeUnit.SECONDS).build();

    public void a(){
        //RateLimiter.create()
    }
}
