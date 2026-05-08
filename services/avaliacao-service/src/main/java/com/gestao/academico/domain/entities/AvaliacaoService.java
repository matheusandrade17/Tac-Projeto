package com.gestao.academico.domain.entities;

import com.gestao.academico.mensageria.listeners.ValidacaoListener;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final ValidacaoListener validacaoListener;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, ValidacaoListener validacaoListener) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.validacaoListener = validacaoListener;
    }

    public Avaliacao salvar(Avaliacao avaliacao) {
        if (avaliacao.getAlunoId() == null) {
            throw new IllegalArgumentException("Aluno ID não pode ser nulo");
        }
        if (avaliacao.getDisciplinaId() == null) {
            throw new IllegalArgumentException("Disciplina ID não pode ser nulo");
        }

        if (!validacaoListener.isAlunoValido(avaliacao.getAlunoId())) {
            throw new RuntimeException("Avaliação negada: Aluno não encontrado no sistema acadêmico.");
        }
        if (!validacaoListener.isDisciplinaValida(avaliacao.getDisciplinaId())) {
            throw new RuntimeException("Avaliação negada: Disciplina não encontrada no sistema acadêmico.");
        }

        return avaliacaoRepository.save(avaliacao);
    }
}


