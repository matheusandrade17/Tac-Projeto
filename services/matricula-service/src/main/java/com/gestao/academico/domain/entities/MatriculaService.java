package com.gestao.academico.domain.entities;

import org.springframework.stereotype.Service;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;

    public MatriculaService(MatriculaRepository matriculaRepository) {
        this.matriculaRepository = matriculaRepository;
    }

    public Matricula salvar(Matricula matricula) {
        if (matricula.getAlunoId() == null) {
            throw new IllegalArgumentException("Aluno ID não pode ser nulo");
        }
        if (matricula.getDisciplinaId() == null) {
            throw new IllegalArgumentException("Disciplina ID não pode ser nulo");
        }
        return matriculaRepository.save(matricula);
    }
}
