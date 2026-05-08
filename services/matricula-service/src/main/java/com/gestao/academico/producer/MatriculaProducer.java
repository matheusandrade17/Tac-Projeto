package com.gestao.academico.producer;

import com.gestao.academico.config.RabbitMQConfig;
import com.gestao.academico.event.MatriculaCriadaEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MatriculaProducer {

    private final RabbitTemplate rabbitTemplate;

    public MatriculaProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarMatriculaCriada(MatriculaCriadaEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.MATRICULA_CRIADA_ROUTING_KEY, event);
    }
}