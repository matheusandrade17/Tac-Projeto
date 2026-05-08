package com.gestao.academico.consumer;

import com.gestao.academico.event.AlunoCriadoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AlunoCriadoConsumer {

    private static final Logger logger = LoggerFactory.getLogger(AlunoCriadoConsumer.class);

    @RabbitListener(queues = "aluno.criado.queue")
    public void consumirAlunoCriado(AlunoCriadoEvent event) {
        logger.info("Recebido evento AlunoCriado: ID={}, Nome={}, Email={}", event.getId(), event.getNome(), event.getEmail());
        // Processar mensagem e registrar log
    }
}