package com.gestao.academico.interfaces.controllers;

import com.gestao.academico.domain.entities.MatriculaCacheFacade;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cache")
public class CacheAdminController {

    private final CacheManager cacheManager;
    private final MatriculaCacheFacade matriculaCacheFacade;

    public CacheAdminController(CacheManager cacheManager, MatriculaCacheFacade matriculaCacheFacade) {
        this.cacheManager = cacheManager;
        this.matriculaCacheFacade = matriculaCacheFacade;
    }

    @DeleteMapping("/matriculas")
    public ResponseEntity<Void> evictAllMatriculas() {
        matriculaCacheFacade.evictAllMatriculas();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/matriculas/{id}")
    public ResponseEntity<Void> evictMatricula(@PathVariable Long id) {
        matriculaCacheFacade.evictMatriculaById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/_local")
    public ResponseEntity<Void> evictAllFromCacheManager() {
        if (cacheManager != null) {
            for (String cacheName : cacheManager.getCacheNames()) {
                if (cacheManager.getCache(cacheName) != null) {
                    cacheManager.getCache(cacheName).clear();
                }
            }
        }
        return ResponseEntity.noContent().build();
    }
}

