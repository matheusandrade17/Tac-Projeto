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
        return new PostgresHealthIndicator(jdbcTemplate, 0, "postgres");
    }

    @Bean
    public HealthIndicator redisHealthIndicator(RedisConnectionFactory redisConnectionFactory) {
        return new RedisHealthIndicator(redisConnectionFactory, 0, "redis");
    }

    @Bean
    public HealthIndicator rabbitmqHealthIndicator() {
        // Em ambientes de teste (ex: Testcontainers) não há ConnectionFactory RabbitMQ.
        // Para evitar falhas de inicialização do contexto, não instanciamos o health indicator.
        return () -> org.springframework.boot.actuate.health.Health.unknown().build();
    }


}