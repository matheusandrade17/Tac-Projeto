package com.gestao.academico.observability.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;

public class RedisHealthIndicator implements HealthIndicator {

    private final RedisConnectionFactory redisConnectionFactory;
    private final int timeoutSeconds;
    private final String redisLabel;

    public RedisHealthIndicator(RedisConnectionFactory redisConnectionFactory, int timeoutSeconds, String redisLabel) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.timeoutSeconds = timeoutSeconds;
        this.redisLabel = redisLabel;
    }

    @Override
    public Health health() {
        try (RedisConnection connection = redisConnectionFactory.getConnection()) {
            // Comportamento "deep": execução de comando real (PING).
            // RedisConnection timeout configura-se via factory/cliente; mantemos timeout apenas como metadado.
            String ping = connection.ping();

            if ("PONG".equalsIgnoreCase(ping) || "OK".equalsIgnoreCase(ping)) {
                return Health.up()
                        .withDetail("redis", redisLabel)
                        .withDetail("command", "PING")
                        .withDetail("result", ping)
                        .build();
            }

            return Health.down()
                    .withDetail("redis", redisLabel)
                    .withDetail("command", "PING")
                    .withDetail("result", ping)
                    .build();
        } catch (Exception e) {
            return Health.down(e)
                    .withDetail("redis", redisLabel)
                    .withDetail("command", "PING")
                    .withDetail("timeoutSeconds", timeoutSeconds)
                    .build();
        }
    }
}