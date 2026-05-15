package com.gestao.academico.domain.entities;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gestao.academico.config.CacheNames;

@Service
public class DisciplinaCacheFacade {

    private final DisciplinaControllerFacade disciplinaControllerFacade;

    public DisciplinaCacheFacade(DisciplinaControllerFacade disciplinaControllerFacade) {
        this.disciplinaControllerFacade = disciplinaControllerFacade;
    }

    @Cacheable(cacheNames = CacheNames.DISCIPLINAS_POR_ID, key = "#id")
    public Optional<Disciplina> buscarPorIdCached(Long id) {
        return disciplinaControllerFacade.buscarPorId(id);
    }




    @CacheEvict(cacheNames = CacheNames.DISCIPLINAS_POR_ID, key = "#id")
    public void evictDisciplinaById(Long id) {
        // vazio de propósito: anotação faz a invalidação
    }

    @CacheEvict(cacheNames = CacheNames.DISCIPLINAS_POR_ID, allEntries = true)
    public void evictAllDisciplinas() {
        // vazio de propósito: anotação faz a invalidação
    }
}

