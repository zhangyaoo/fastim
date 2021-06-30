package com.zyblue.fastim.logic.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.redisson.api.RedissonClient;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Resource;


/**
 * @author will
 * @date 2021/6/24 14:19
 */

@Configuration
public class RedisConfig {
    @Resource
    private RedissonClient redissonClient;

    @Bean(name = "customRedisTemplate")
    public RedisTemplate<String, Object> stringRedisTemplate(@Qualifier("connectionFactory") RedissonConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(mapper);

        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(genericJackson2JsonRedisSerializer);
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(genericJackson2JsonRedisSerializer);

        template.setConnectionFactory(factory);
        template.afterPropertiesSet();
        return template;
    }

    @Bean(name = "connectionFactory")
    public RedissonConnectionFactory redissonConnectionFactory() {
        return new RedissonConnectionFactory(redissonClient);
    }
}

