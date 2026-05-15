package com.gestao.academico.cache;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Component;

import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheableOperation;
import org.springframework.cache.interceptor.CacheEvictOperation;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.util.StringUtils;

@Aspect
@Component
public class CacheLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(CacheLoggingAspect.class);

    private final RedisTemplate<String, Object> redisTemplate;

    public CacheLoggingAspect(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Around("@annotation(org.springframework.cache.annotation.Cacheable) || @annotation(org.springframework.cache.annotation.CacheEvict)")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.nanoTime();
        try {
            // Log leve: cacheName/key não está sempre disponível aqui sem inspecionar o contexto.
            // Para PoC, focamos em evidenciar o uso do cache através do Redis TTL e da existência da chave.
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

    // Futuro aprimoramento: interceptar com CacheOperationInvocationContext e resolver keys com precisão.
}

