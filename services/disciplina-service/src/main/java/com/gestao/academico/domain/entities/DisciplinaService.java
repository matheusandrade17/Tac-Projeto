package com.gestao.academico.domain.entities;

import org.springframework.stereotype.Service;

@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    public Disciplina salvar(Disciplina disciplina) {
        if (disciplina.getNome() == null || disciplina.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio ou nulo");
        }
        if (disciplina.getCodigo() == null || disciplina.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("Código não pode ser vazio ou nulo");
        }
        return disciplinaRepository.save(disciplina);
    }
}
