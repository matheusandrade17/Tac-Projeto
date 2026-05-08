package com.gestao.academico.domain.entities;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public Aluno salvar(Aluno aluno) {
        if (aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio ou nulo");
        }
        return alunoRepository.save(aluno);
    }


    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    @SuppressWarnings("null")
    public Optional<Aluno> buscarPorId(Long id) {
        return alunoRepository.findById(id);
    }

    @SuppressWarnings("null")
    public void remover(Long id) {
        alunoRepository.deleteById(id);
    }
}