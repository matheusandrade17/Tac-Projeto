package com.gestao.academico.infrastructure.repository;

import com.gestao.academico.domain.entities.Disciplina;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryDisciplinaRepository {
    private final Map<String, Disciplina> store = new LinkedHashMap<>();

    public List<Disciplina> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Disciplina> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public Disciplina save(Disciplina disciplina) {
        if (disciplina.getId() == null || disciplina.getId().isBlank()) {
            disciplina.setId(UUID.randomUUID().toString());
        }
        store.put(disciplina.getId(), disciplina);
        return disciplina;
    }

    public void deleteById(String id) {
        store.remove(id);
    }

    public boolean existsById(String id) {
        return store.containsKey(id);
    }
}
