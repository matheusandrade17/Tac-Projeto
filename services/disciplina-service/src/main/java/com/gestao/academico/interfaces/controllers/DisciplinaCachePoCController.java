package com.gestao.academico.interfaces.controllers;

import com.gestao.academico.domain.entities.Disciplina;
import com.gestao.academico.domain.entities.DisciplinaCacheFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/disciplinas-cache")
@Tag(name = "Disciplinas Cache", description = "PoC de cache Redis para disciplinas")
public class DisciplinaCachePoCController {

    private final DisciplinaCacheFacade disciplinaCacheFacade;

    public DisciplinaCachePoCController(DisciplinaCacheFacade disciplinaCacheFacade) {
        this.disciplinaCacheFacade = disciplinaCacheFacade;
    }

    @Operation(summary = "Buscar disciplina cacheada por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorIdCache(@PathVariable Long id) {
        Optional<Disciplina> disciplina = disciplinaCacheFacade.buscarPorIdCached(id);
        if (disciplina.isEmpty()) {
            return ResponseEntity.status(404).body(java.util.Map.of(
                    "type", "https://gestao.academico/probs/disciplina-nao-encontrada",
                    "title", "Disciplina não encontrada",
                    "status", 404,
                    "detail", "Não existe disciplina com id " + id,
                    "instance", "/api/v1/disciplinas-cache/" + id
            ));
        }
        return ResponseEntity.ok(disciplina.get());
    }
}

