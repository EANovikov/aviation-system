package com.xevgnov.aviation.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableRetry
@EnableCaching
@EnableScheduling
public class ApplicationConfig {
    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager("airports");
        cacheManager.setAllowNullValues(false);
        return cacheManager;
    }

    @CacheEvict(value = "airports", allEntries = true)
    @Scheduled(fixedDelayString = "${airport.client.cache.evictIntervalMs:10000}", initialDelay = 0)
    public void evictAllCachesAtIntervals() {
        log.info("Evicting all entries from airports cache");
    }
}
