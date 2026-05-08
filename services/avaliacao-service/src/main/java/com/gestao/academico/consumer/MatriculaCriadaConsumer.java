package com.gestao.academico.consumer;

import com.gestao.academico.event.MatriculaCriadaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MatriculaCriadaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MatriculaCriadaConsumer.class);

    @RabbitListener(queues = "matricula.criada.queue")
    public void consumirMatriculaCriada(MatriculaCriadaEvent event) {
        logger.info("Recebido evento MatriculaCriada: ID={}, AlunoID={}, Disciplina={}", event.getId(), event.getAlunoId(), event.getCodigoDisciplina());
        // Processar mensagem e registrar log
    }
}