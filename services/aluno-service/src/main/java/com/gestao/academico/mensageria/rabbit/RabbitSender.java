package com.gestao.academico.mensageria.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${messageria.exchange:tac.academico.events}")
    private String exchange;

    public RabbitSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String routingKey, Object eventPayload) {
        rabbitTemplate.convertAndSend(exchange, routingKey, eventPayload);
    }
}


