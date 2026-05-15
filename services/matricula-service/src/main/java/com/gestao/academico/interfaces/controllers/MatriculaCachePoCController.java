package com.gestao.academico.interfaces.controllers;

import com.gestao.academico.domain.entities.Matricula;
import com.gestao.academico.domain.entities.MatriculaCacheFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/matriculas-cache")
@Tag(name = "Matrículas Cache", description = "PoC de cache Redis para matrículas")
public class MatriculaCachePoCController {

    private final MatriculaCacheFacade matriculaCacheFacade;

    public MatriculaCachePoCController(MatriculaCacheFacade matriculaCacheFacade) {
        this.matriculaCacheFacade = matriculaCacheFacade;
    }

    @Operation(summary = "Buscar matrícula cacheada por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorIdCache(@PathVariable Long id) {
        Optional<Matricula> matricula = matriculaCacheFacade.buscarPorIdCached(id);
        if (matricula.isEmpty()) {
            return ResponseEntity.status(404).body(java.util.Map.of(
                    "type", "https://gestao.academico/probs/matricula-nao-encontrada",
                    "title", "Matrícula não encontrada",
                    "status", 404,
                    "detail", "Não existe matrícula com id " + id,
                    "instance", "/api/v1/matriculas-cache/" + id
            ));
        }
        return ResponseEntity.ok(matricula.get());
    }
}

