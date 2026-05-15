package com.gestao.academico.domain.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

/**
 * Mantém a lista em memória como fonte de verdade.
 * Esse facade existe apenas para permitir que controllers usem @Cacheable sem
 * precisar replicar lógica de stream diretamente no controller.
 */
@Service
public class DisciplinaControllerFacade {

    private static final List<Disciplina> disciplinas = new ArrayList<>();

    public List<Disciplina> listarTodos() {
        return disciplinas;
    }

    public Optional<Disciplina> buscarPorId(Long id) {
        return disciplinas.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
    }

    public Disciplina cadastrar(Disciplina novaDisciplina) {
        long novoId = disciplinas.stream()
                .mapToLong(Disciplina::getId)
                .max()
                .orElse(0) + 1;
        novaDisciplina.setId(novoId);
        disciplinas.add(novaDisciplina);
        return novaDisciplina;
    }

    public boolean atualizar(Long id, Disciplina disciplinaAtualizada) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getId().equals(id)) {
                disciplina.setNome(disciplinaAtualizada.getNome());
                disciplina.setCodigo(disciplinaAtualizada.getCodigo());
                disciplina.setDescricao(disciplinaAtualizada.getDescricao());
                disciplina.setCargaHoraria(disciplinaAtualizada.getCargaHoraria());
                return true;
            }
        }
        return false;
    }

    public boolean remover(Long id) {
        return disciplinas.removeIf(d -> d.getId().equals(id));
    }

    public void evictAllLocal() {
        // Mantemos apenas para possível reset futuro; não é usado no PoC.
        disciplinas.clear();
    }
}

