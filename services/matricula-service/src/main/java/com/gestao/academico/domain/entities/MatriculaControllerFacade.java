package com.gestao.academico.domain.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


@Service
public class MatriculaControllerFacade {

    private static final List<Matricula> matriculas = new ArrayList<>();

    public List<Matricula> listarTodos() {
        return matriculas;
    }

    public Optional<Matricula> buscarPorId(Long id) {
        return matriculas.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }

    public Matricula cadastrar(Matricula novaMatricula) {
        long novoId = matriculas.stream()
                .mapToLong(Matricula::getId)
                .max()
                .orElse(0) + 1;
        novaMatricula.setId(novoId);
        matriculas.add(novaMatricula);
        return novaMatricula;
    }

    public boolean atualizar(Long id, Matricula matriculaAtualizada) {
        for (Matricula matricula : matriculas) {
            if (matricula.getId().equals(id)) {
                matricula.setAlunoId(matriculaAtualizada.getAlunoId());
                matricula.setDisciplinaId(matriculaAtualizada.getDisciplinaId());
                matricula.setDataMatricula(matriculaAtualizada.getDataMatricula());
                matricula.setStatus(matriculaAtualizada.getStatus());
                return true;
            }
        }
        return false;
    }

    public boolean remover(Long id) {
        return matriculas.removeIf(m -> m.getId().equals(id));
    }
}

