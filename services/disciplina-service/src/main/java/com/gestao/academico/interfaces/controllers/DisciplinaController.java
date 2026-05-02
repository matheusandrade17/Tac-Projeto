package com.gestao.academico.interfaces.controllers;

import com.gestao.academico.domain.entities.Disciplina;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/disciplinas")
@Tag(name = "Disciplinas", description = "Endpoints para gerenciamento de disciplinas")
public class DisciplinaController {

    private static List<Disciplina> disciplinas = new ArrayList<>();

    @Operation(summary = "Listar todas as disciplinas", description = "Retorna uma lista com todas as disciplinas cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de disciplinas retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Disciplina.class)))
    @GetMapping
    public List<Disciplina> listarTodos() {
        return disciplinas;
    }

    @Operation(summary = "Buscar disciplina por ID", description = "Retorna uma disciplina específica com base no identificador fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disciplina encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Disciplina.class))),
            @ApiResponse(responseCode = "404", description = "Disciplina não encontrada",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(
            @Parameter(description = "ID da disciplina a ser buscada", example = "1")
            @PathVariable Long id) {
        Optional<Disciplina> disciplina = disciplinas.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();

        if (disciplina.isEmpty()) {
            java.util.Map<String, Object> erro = new java.util.LinkedHashMap<>();
            erro.put("type", "https://gestao.academico/probs/disciplina-nao-encontrada");
            erro.put("title", "Disciplina não encontrada");
            erro.put("status", 404);
            erro.put("detail", "Não existe disciplina com id " + id);
            erro.put("instance", "/api/v1/disciplinas/" + id);
            return ResponseEntity.status(404).body(erro);
        }

        return ResponseEntity.ok(disciplina.get());
    }

    @Operation(summary = "Cadastrar nova disciplina", description = "Cria um novo registro de disciplina no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Disciplina criada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Disciplina.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<Disciplina> cadastrar(@RequestBody Disciplina novaDisciplina) {
        long novoId = disciplinas.stream()
                .mapToLong(Disciplina::getId)
                .max()
                .orElse(0) + 1;
        novaDisciplina.setId(novoId);
        disciplinas.add(novaDisciplina);
        return ResponseEntity.status(201).body(novaDisciplina);
    }

    @Operation(summary = "Atualizar disciplina", description = "Atualiza os dados de uma disciplina existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Disciplina atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Disciplina não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(
            @Parameter(description = "ID da disciplina a ser atualizada", example = "1")
            @PathVariable Long id,
            @RequestBody Disciplina disciplinaAtualizada) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getId().equals(id)) {
                disciplina.setNome(disciplinaAtualizada.getNome());
                disciplina.setCodigo(disciplinaAtualizada.getCodigo());
                disciplina.setDescricao(disciplinaAtualizada.getDescricao());
                disciplina.setCargaHoraria(disciplinaAtualizada.getCargaHoraria());
                return ResponseEntity.noContent().build();
            }
        }
        java.util.Map<String, Object> erro = new java.util.LinkedHashMap<>();
        erro.put("type", "https://gestao.academico/probs/disciplina-nao-encontrada");
        erro.put("title", "Disciplina não encontrada");
        erro.put("status", 404);
        erro.put("detail", "Não existe disciplina com id " + id);
        erro.put("instance", "/api/v1/disciplinas/" + id);
        return ResponseEntity.status(404).body(erro);
    }

    @Operation(summary = "Remover disciplina", description = "Remove uma disciplina do sistema com base no ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Disciplina removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Disciplina não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remover(
            @Parameter(description = "ID da disciplina a ser removida", example = "1")
            @PathVariable Long id) {
        boolean removido = disciplinas.removeIf(d -> d.getId().equals(id));

        if (!removido) {
            java.util.Map<String, Object> erro = new java.util.LinkedHashMap<>();
            erro.put("type", "https://gestao.academico/probs/disciplina-nao-encontrada");
            erro.put("title", "Disciplina não encontrada");
            erro.put("status", 404);
            erro.put("detail", "Não existe disciplina com id " + id);
            erro.put("instance", "/api/v1/disciplinas/" + id);
            return ResponseEntity.status(404).body(erro);
        }

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Verificar status da API", description = "Retorna informações sobre a versão e status da API")
    @ApiResponse(responseCode = "200", description = "Status retornado com sucesso")
    @GetMapping("/status")
    public String checkStatus() {
        return "{\"version\": \"v1\", \"status\": \"Online\", \"architecture\": \"Clean Architecture\"}";
    }
}
