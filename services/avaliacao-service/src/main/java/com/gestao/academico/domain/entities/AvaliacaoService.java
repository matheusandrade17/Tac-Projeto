package com.gestao.academico.domain.entities;

import com.gestao.academico.integration.AlunoClient;
import com.gestao.academico.integration.DisciplinaClient;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AlunoClient alunoClient;
    private final DisciplinaClient disciplinaClient;

    public AvaliacaoService(
            AvaliacaoRepository avaliacaoRepository,
            AlunoClient alunoClient,
            DisciplinaClient disciplinaClient
    ) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.alunoClient = alunoClient;
        this.disciplinaClient = disciplinaClient;
    }

    public Avaliacao salvar(Avaliacao avaliacao) {
        if (avaliacao.getAlunoId() == null) {
            throw new IllegalArgumentException("Aluno ID nao pode ser nulo");
        }
        if (avaliacao.getDisciplinaId() == null) {
            throw new IllegalArgumentException("Disciplina ID nao pode ser nulo");
        }

        if (!alunoClient.alunoExiste(avaliacao.getAlunoId()).join()) {
            throw new RuntimeException("Avaliacao negada: Aluno nao encontrado no sistema academico.");
        }
        if (!disciplinaClient.disciplinaExiste(avaliacao.getDisciplinaId()).join()) {
            throw new RuntimeException("Avaliacao negada: Disciplina nao encontrada no sistema academico.");
        }

        return avaliacaoRepository.save(avaliacao);
    }
}
