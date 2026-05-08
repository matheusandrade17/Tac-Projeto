package com.gestao.academico.mensageria.listeners;

import com.gestao.academico.mensageria.events.AlunoCriadoEvent;
import com.gestao.academico.mensageria.rabbit.RabbitConfig;
import com.gestao.academico.mensageria.rabbit.RabbitSender;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AlunoCriadoPublisher {

    private final RabbitSender rabbitSender;

    public AlunoCriadoPublisher(RabbitSender rabbitSender) {
        this.rabbitSender = rabbitSender;
    }

    public void publishAlunoCriado(Long alunoId, String nome) {
        AlunoCriadoEvent event = new AlunoCriadoEvent(alunoId, nome, Instant.now());
        rabbitSender.send(RabbitConfig.RK_ALUNO_CRIADO, event);
    }
}

