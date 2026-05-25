package com.gestao.academico.observability.health;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

public class RabbitMQHealthIndicator implements HealthIndicator {

    private final ConnectionFactory connectionFactory;
    private final int timeoutSeconds;
    private final String rabbitLabel;

    public RabbitMQHealthIndicator(ConnectionFactory connectionFactory, int timeoutSeconds, String rabbitLabel) {
        this.connectionFactory = connectionFactory;
        this.timeoutSeconds = timeoutSeconds;
        this.rabbitLabel = rabbitLabel;
    }

    @Override
    public Health health() {
        try (Connection connection = connectionFactory.newConnection("observability-healthcheck")) {
            if (connection != null && connection.isOpen()) {
                return Health.up()
                        .withDetail("rabbitmq", rabbitLabel)
                        .withDetail("command", "open-connection")
                        .build();
            }

            return Health.down()
                    .withDetail("rabbitmq", rabbitLabel)
                    .withDetail("command", "open-connection")
                    .build();
        } catch (Exception e) {
            return Health.down(e)
                    .withDetail("rabbitmq", rabbitLabel)
                    .withDetail("timeoutSeconds", timeoutSeconds)
                    .withDetail("command", "open-connection")
                    .build();
        }
    }
}