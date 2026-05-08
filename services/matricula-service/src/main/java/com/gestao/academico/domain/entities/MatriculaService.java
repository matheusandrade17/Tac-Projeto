package com.gestao.academico.domain.entities;

import com.gestao.academico.event.MatriculaCriadaEvent;
import com.gestao.academico.mensageria.listeners.ValidacaoListener;
import com.gestao.academico.producer.MatriculaProducer;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final ValidacaoListener validacaoListener;
    private final MatriculaProducer matriculaProducer;

    public MatriculaService(MatriculaRepository matriculaRepository, ValidacaoListener validacaoListener, MatriculaProducer matriculaProducer) {
        this.matriculaRepository = matriculaRepository;
        this.validacaoListener = validacaoListener;
        this.matriculaProducer = matriculaProducer;
    }

    public Matricula salvar(Matricula matricula) {
        if (matricula.getAlunoId() == null) {
            throw new IllegalArgumentException("Aluno ID não pode ser nulo");
        }
        if (matricula.getDisciplinaId() == null) {
            throw new IllegalArgumentException("Disciplina ID não pode ser nulo");
        }

        if (!validacaoListener.isAlunoValido(matricula.getAlunoId())) {
            throw new RuntimeException("Matrícula negada: Aluno não encontrado no sistema acadêmico.");
        }
        if (!validacaoListener.isDisciplinaValida(matricula.getDisciplinaId())) {
            throw new RuntimeException("Matrícula negada: Disciplina não encontrada no sistema acadêmico.");
        }

        Matricula saved = matriculaRepository.save(matricula);
        MatriculaCriadaEvent event = new MatriculaCriadaEvent(saved.getId(), saved.getAlunoId(), saved.getDisciplinaId().toString(), LocalDateTime.now());
        matriculaProducer.publicarMatriculaCriada(event);
        return saved;
    }
}

