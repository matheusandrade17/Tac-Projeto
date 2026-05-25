package com.gestao.academico.observability.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;

public class PostgresHealthIndicator implements HealthIndicator {

    private final JdbcTemplate jdbcTemplate;
    private final int timeoutSeconds;
    private final String dataSourceLabel;

    public PostgresHealthIndicator(JdbcTemplate jdbcTemplate, int timeoutSeconds, String dataSourceLabel) {
        this.jdbcTemplate = jdbcTemplate;
        this.timeoutSeconds = timeoutSeconds;
        this.dataSourceLabel = dataSourceLabel;
    }

    @Override
    public Health health() {
        try {
            // Checagem simples e efetiva: conexão e execução de query.
            jdbcTemplate.setQueryTimeout(timeoutSeconds);

            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);

            if (result != null && result == 1) {
                return Health.up()
                        .withDetail("datasource", dataSourceLabel)
                        .withDetail("query", "SELECT 1")
                        .build();
            }

            return Health.down()
                    .withDetail("datasource", dataSourceLabel)
                    .withDetail("query", "SELECT 1")
                    .withDetail("result", result)
                    .build();
        } catch (Exception e) {
            return Health.down(e)
                    .withDetail("datasource", dataSourceLabel)
                    .withDetail("query", "SELECT 1")
                    .withDetail("timeoutSeconds", timeoutSeconds)
                    .build();
        }
    }
}