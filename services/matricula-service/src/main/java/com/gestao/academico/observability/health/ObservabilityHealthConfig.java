package com.gestao.academico.observability.health;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ObservabilityHealthConfig {

    @Bean
    public HealthIndicator postgresHealthIndicator(JdbcTemplate jdbcTemplate) {
        return new PostgresHealthIndicator(jdbcTemplate, "postgres");
    }

    @Bean
    public HealthIndicator redisHealthIndicator(RedisConnectionFactory redisConnectionFactory) {
        return new RedisHealthIndicator(redisConnectionFactory, "redis");
    }

    @Bean
    public HealthIndicator rabbitmqHealthIndicator(ConnectionFactory connectionFactory) {
        return new RabbitMQHealthIndicator(connectionFactory, "rabbitmq");
    }
}