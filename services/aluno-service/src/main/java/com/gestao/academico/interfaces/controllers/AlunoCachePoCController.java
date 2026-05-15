package com.gestao.academico.interfaces.controllers;

import com.gestao.academico.config.CacheNames;
import com.gestao.academico.domain.entities.Aluno;
import com.gestao.academico.domain.entities.AlunoCacheFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/alunos-cache")
public class AlunoCachePoCController {

    private final AlunoCacheFacade alunoCacheFacade;

    public AlunoCachePoCController(AlunoCacheFacade alunoCacheFacade) {
        this.alunoCacheFacade = alunoCacheFacade;
    }

    @Operation(summary = "PoC Redis Cache - Buscar aluno por ID (Cacheable)")
    @ApiResponse(responseCode = "200")
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorIdCacheable(@PathVariable Long id) {
        Optional<Aluno> aluno = alunoCacheFacade.buscarPorIdCached(id);
        return aluno.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/status")
    public String status() {
        return "{\"cacheName\":\"" + CacheNames.ALUNOS_POR_ID + "\"}";
    }
}

