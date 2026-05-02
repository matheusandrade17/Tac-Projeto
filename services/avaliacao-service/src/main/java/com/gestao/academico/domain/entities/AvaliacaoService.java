package com.gestao.academico.domain.entities;

import org.springframework.stereotype.Service;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public Avaliacao salvar(Avaliacao avaliacao) {
        if (avaliacao.getAlunoId() == null) {
            throw new IllegalArgumentException("Aluno ID não pode ser nulo");
        }
        if (avaliacao.getDisciplinaId() == null) {
            throw new IllegalArgumentException("Disciplina ID não pode ser nulo");
        }
        return avaliacaoRepository.save(avaliacao);
    }
}
