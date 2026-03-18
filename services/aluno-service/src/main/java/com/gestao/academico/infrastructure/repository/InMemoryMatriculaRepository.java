package com.gestao.academico.infrastructure.repository;

import com.gestao.academico.domain.entities.Matricula;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryMatriculaRepository {
    private final Map<String, Matricula> store = new LinkedHashMap<>();

    public List<Matricula> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Matricula> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public Matricula save(Matricula matricula) {
        if (matricula.getId() == null || matricula.getId().isBlank()) {
            matricula.setId(UUID.randomUUID().toString());
        }
        store.put(matricula.getId(), matricula);
        return matricula;
    }

    public void deleteById(String id) {
        store.remove(id);
    }

    public boolean existsById(String id) {
        return store.containsKey(id);
    }
}
