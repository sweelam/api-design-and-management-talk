package com.flight.search.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisClient {
    private final Logger logger = LoggerFactory.getLogger("RedisClient");
    private final RedisTemplate<String, Object> redisTemplate;
    private ValueOperations<String, Object> valueOps;

    @PostConstruct
    void init() {
        valueOps = redisTemplate.opsForValue();
    }

    public Object get(String key) {
        logger.info("Fetch cache value with key [{}]", key);
        var o = valueOps.get(key);
        if (o != null) logger.info("Cache value with key [{}] found", key);
        return o;
    }

    public void set(String key, Object value, long ttl) {
        valueOps.set(key, value, ttl, TimeUnit.MINUTES);
    }

    public void replace(String key, Object value, long ttl) {
        valueOps.setIfPresent(key, value, Duration.of(ttl, ChronoUnit.MINUTES));
    }

    public <T> T setAndGet(String key, T value, long ttl) {
        logger.info("Add new cache value with key {}", key);

        valueOps.set(key, value, ttl, TimeUnit.MINUTES);
        return value;
    }

}
