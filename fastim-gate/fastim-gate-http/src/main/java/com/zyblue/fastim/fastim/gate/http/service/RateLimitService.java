package com.zyblue.fastim.fastim.gate.http.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
    //private final Cache<String, Integer> cache = CacheBuilder.newBuilder().expireAfterWrite(limit, TimeUnit.SECONDS).build();
}
