package com.gestao.academico.messaging;

import com.gestao.academico.event.AlunoCriadoEvent;
import com.gestao.academico.model.Aluno;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoEventProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarAlunoCriado(Aluno aluno) {

        AlunoCriadoEvent event =
                new AlunoCriadoEvent(aluno.getId(), aluno.getNome(), aluno.getEmail());

        rabbitTemplate.convertAndSend(
                "gestao.exchange",
                "aluno.criado",
                event
        );
    }
}