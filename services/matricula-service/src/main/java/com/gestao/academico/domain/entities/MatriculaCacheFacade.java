package com.gestao.academico.domain.entities;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gestao.academico.config.CacheNames;

@Service
public class MatriculaCacheFacade {

    private final MatriculaRepository matriculaRepository;

    public MatriculaCacheFacade(MatriculaRepository matriculaRepository) {
        this.matriculaRepository = matriculaRepository;
    }

    @Cacheable(cacheNames = CacheNames.MATRICULAS_POR_ID, key = "#id")
    public Optional<Matricula> buscarPorIdCached(Long id) {
        return matriculaRepository.findById(id);
    }

    @CacheEvict(cacheNames = CacheNames.MATRICULAS_POR_ID, key = "#id")
    public void evictMatriculaById(Long id) {
        // vazio de propósito: anotação faz a invalidação
    }

    @CacheEvict(cacheNames = CacheNames.MATRICULAS_POR_ID, allEntries = true)
    public void evictAllMatriculas() {
        // vazio de propósito: anotação faz a invalidação
    }
}


