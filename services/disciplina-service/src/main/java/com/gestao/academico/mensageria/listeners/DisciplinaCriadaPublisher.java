package com.gestao.academico.mensageria.listeners;

import com.gestao.academico.mensageria.events.DisciplinaCriadaEvent;
import com.gestao.academico.mensageria.rabbit.RabbitConfig;
import com.gestao.academico.mensageria.rabbit.RabbitSender;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DisciplinaCriadaPublisher {

    private final RabbitSender rabbitSender;

    public DisciplinaCriadaPublisher(RabbitSender rabbitSender) {
        this.rabbitSender = rabbitSender;
    }

    public void publishDisciplinaCriada(Long disciplinaId, String codigo) {
        DisciplinaCriadaEvent event = new DisciplinaCriadaEvent(disciplinaId, codigo, Instant.now());
        rabbitSender.send(RabbitConfig.RK_DISCIPLINA_CRIADA, event);
    }
}


