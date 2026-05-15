package com.gestao.academico.domain.entities;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gestao.academico.config.CacheNames;

@Service
public class AlunoCacheFacade {

    private final AlunoService alunoService;

    public AlunoCacheFacade(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @Cacheable(cacheNames = CacheNames.ALUNOS_POR_ID, key = "#id")
    public Optional<Aluno> buscarPorIdCached(Long id) {
        return alunoService.buscarPorId(id);
    }



    @CacheEvict(cacheNames = CacheNames.ALUNOS_POR_ID, key = "#id")
    public void evictAlunoById(Long id) {
        // vazio de propósito: anotação faz a invalidação
    }


    @CacheEvict(cacheNames = CacheNames.ALUNOS_POR_ID, allEntries = true)
    public void evictAllAlunos() {
        // vazio de propósito: anotação faz a invalidação
    }


}

