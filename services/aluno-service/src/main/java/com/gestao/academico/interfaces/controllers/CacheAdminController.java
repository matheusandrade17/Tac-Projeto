package com.gestao.academico.interfaces.controllers;

import com.gestao.academico.config.CacheNames;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/cache")
public class CacheAdminController {

    private final CacheManager cacheManager;

    public CacheAdminController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @DeleteMapping("/alunos/{id}")
    public ResponseEntity<Map<String, Object>> evictAlunoPorId(@PathVariable Long id) {
        // Cache Eviction é via CacheManager para garantir ação mesmo se o facade não for usado.
        if (cacheManager.getCache(CacheNames.ALUNOS_POR_ID) != null) {
            cacheManager.getCache(CacheNames.ALUNOS_POR_ID).evict(id);
        }
        return ResponseEntity.ok(Map.of(
                "cacheName", CacheNames.ALUNOS_POR_ID,
                "evictedKey", id,
                "status", "OK"
        ));
    }

    @DeleteMapping("/alunos")
    public ResponseEntity<Map<String, Object>> evictTodosAlunos() {
        if (cacheManager.getCache(CacheNames.ALUNOS_POR_ID) != null) {
            cacheManager.getCache(CacheNames.ALUNOS_POR_ID).clear();
        }
        return ResponseEntity.ok(Map.of(
                "cacheName", CacheNames.ALUNOS_POR_ID,
                "status", "OK"
        ));
    }
}

