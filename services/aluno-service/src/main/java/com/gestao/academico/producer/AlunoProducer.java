package com.gestao.academico.producer;

import com.gestao.academico.config.RabbitMQConfig;
import com.gestao.academico.event.AlunoCriadoEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AlunoProducer {

    private final RabbitTemplate rabbitTemplate;

    public AlunoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarAlunoCriado(AlunoCriadoEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ALUNO_CRIADO_ROUTING_KEY, event);
    }
}