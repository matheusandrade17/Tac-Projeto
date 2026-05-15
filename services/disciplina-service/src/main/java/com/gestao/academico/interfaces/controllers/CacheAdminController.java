package com.gestao.academico.interfaces.controllers;

import com.gestao.academico.config.CacheNames;
import com.gestao.academico.domain.entities.DisciplinaCacheFacade;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cache")
public class CacheAdminController {

    private final CacheManager cacheManager;
    private final DisciplinaCacheFacade disciplinaCacheFacade;

    public CacheAdminController(CacheManager cacheManager, DisciplinaCacheFacade disciplinaCacheFacade) {
        this.cacheManager = cacheManager;
        this.disciplinaCacheFacade = disciplinaCacheFacade;
    }

    @DeleteMapping("/disciplinas")
    public ResponseEntity<Void> evictAllDisciplinas() {
        disciplinaCacheFacade.evictAllDisciplinas();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/disciplinas/{id}")
    public ResponseEntity<Void> evictDisciplina(@PathVariable Long id) {
        disciplinaCacheFacade.evictDisciplinaById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/_local")
    public ResponseEntity<Void> evictAllFromCacheManager() {
        if (cacheManager != null) {
            cacheManager.getCacheNames();
            for (String cacheName : cacheManager.getCacheNames()) {
                if (cacheManager.getCache(cacheName) != null) {
                    cacheManager.getCache(cacheName).clear();
                }
            }
        }
        return ResponseEntity.noContent().build();
    }
}

