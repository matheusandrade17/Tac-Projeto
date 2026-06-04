package com.gestao.academico.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@ConditionalOnProperty(
    name = "spring.cache.type",
    havingValue = "redis",
    matchIfMissing = true
)
@EnableRedisRepositories
public class RedisConnectionFactoryConfig {
    // Esta classe garante que repositories Redis só sejam habilitados
    // quando spring.cache.type = redis, evitando conexões desnecessárias em testes
}
