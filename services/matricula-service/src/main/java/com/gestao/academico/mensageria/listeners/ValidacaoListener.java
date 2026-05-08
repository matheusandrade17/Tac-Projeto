package com.gestao.academico.mensageria.listeners;

import com.gestao.academico.mensageria.events.AlunoCriadoEvent;
import com.gestao.academico.mensageria.events.DisciplinaCriadaEvent;
import com.gestao.academico.mensageria.rabbit.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ValidacaoListener {

    private final Set<Long> alunosValidos = ConcurrentHashMap.newKeySet();
    private final Set<Long> disciplinasValidas = ConcurrentHashMap.newKeySet();

    @RabbitListener(queues = RabbitConfig.QUEUE_MATRICULA_VALIDACAO)
    public void handle(Object payload) {
        if (payload instanceof AlunoCriadoEvent e) {
            alunosValidos.add(e.alunoId());
        } else if (payload instanceof DisciplinaCriadaEvent d) {
            disciplinasValidas.add(d.disciplinaId());
        }
    }

    public boolean isAlunoValido(Long alunoId) {
        return alunoId != null && alunosValidos.contains(alunoId);
    }

    public boolean isDisciplinaValida(Long disciplinaId) {
        return disciplinaId != null && disciplinasValidas.contains(disciplinaId);
    }
}

