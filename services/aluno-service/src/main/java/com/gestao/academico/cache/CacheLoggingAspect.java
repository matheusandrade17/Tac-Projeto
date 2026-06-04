package com.gestao.academico.cache;

import java.util.concurrent.TimeUnit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(CacheLoggingAspect.class);
    private final RedisTemplate<String, Object> redisTemplate;

    public CacheLoggingAspect(ObjectProvider<RedisTemplate<String, Object>> redisTemplateProvider) {
        this.redisTemplate = redisTemplateProvider.getIfAvailable();
    }

    @Around("@annotation(org.springframework.cache.annotation.Cacheable) || @annotation(org.springframework.cache.annotation.CacheEvict)")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.nanoTime();
        try {
            Object result = pjp.proceed();
            long ms = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
            log.info("[CACHE] method={} time={}ms", pjp.getSignature().toShortString(), ms);
            return result;
        } catch (Throwable t) {
            long ms = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
            log.warn("[CACHE] method={} failed time={}ms", pjp.getSignature().toShortString(), ms, t);
            throw t;
        }
    }
}


