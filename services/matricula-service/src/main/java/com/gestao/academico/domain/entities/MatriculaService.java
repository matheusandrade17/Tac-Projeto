package com.gestao.academico.domain.entities;

import com.gestao.academico.event.MatriculaCriadaEvent;
import com.gestao.academico.integration.AlunoClient;
import com.gestao.academico.integration.DisciplinaClient;
import com.gestao.academico.producer.MatriculaProducer;
import dto.MatriculaDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlunoClient alunoClient;
    private final DisciplinaClient disciplinaClient;
    private final MatriculaProducer matriculaProducer;

    public MatriculaService(
            MatriculaRepository matriculaRepository,
            AlunoClient alunoClient,
            DisciplinaClient disciplinaClient,
            MatriculaProducer matriculaProducer
    ) {
        this.matriculaRepository = matriculaRepository;
        this.alunoClient = alunoClient;
        this.disciplinaClient = disciplinaClient;
        this.matriculaProducer = matriculaProducer;
    }

    public Matricula salvar(Matricula matricula) {
        if (matricula.getAlunoId() == null) {
            throw new IllegalArgumentException("Aluno ID nao pode ser nulo");
        }
        if (matricula.getDisciplinaId() == null) {
            throw new IllegalArgumentException("Disciplina ID nao pode ser nulo");
        }

        if (!alunoClient.alunoExiste(matricula.getAlunoId()).join()) {
            throw new RuntimeException("Matricula negada: Aluno nao encontrado no sistema academico.");
        }
        if (!disciplinaClient.disciplinaExiste(matricula.getDisciplinaId()).join()) {
            throw new RuntimeException("Matricula negada: Disciplina nao encontrada no sistema academico.");
        }

        Matricula saved = matriculaRepository.save(matricula);
        MatriculaCriadaEvent event = new MatriculaCriadaEvent(
                saved.getId(),
                saved.getAlunoId(),
                saved.getDisciplinaId().toString(),
                LocalDateTime.now()
        );
        matriculaProducer.publicarMatriculaCriada(event);
        return saved;
    }

    public model.Matricula criarMatricula(MatriculaDto dto) {
        model.Matricula matricula = new model.Matricula();
        matricula.setClienteId(dto.getAlunoId());
        matricula.setCriadoEm(LocalDateTime.now());
        return matricula;
    }
}
