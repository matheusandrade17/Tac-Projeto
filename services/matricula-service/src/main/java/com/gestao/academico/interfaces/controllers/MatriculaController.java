package com.gestao.academico.interfaces.controllers;

import com.gestao.academico.domain.entities.Matricula;
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
@RequestMapping("/api/v1/matriculas")
@Tag(name = "Matrículas", description = "Endpoints para gerenciamento de matrículas")
public class MatriculaController {

    private static List<Matricula> matriculas = new ArrayList<>();

    @Operation(summary = "Listar todas as matrículas", description = "Retorna uma lista com todas as matrículas cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de matrículas retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Matricula.class)))
    @GetMapping
    public List<Matricula> listarTodos() {
        return matriculas;
    }

    @Operation(summary = "Buscar matrícula por ID", description = "Retorna uma matrícula específica com base no identificador fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matrícula encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Matricula.class))),
            @ApiResponse(responseCode = "404", description = "Matrícula não encontrada",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(
            @Parameter(description = "ID da matrícula a ser buscada", example = "1")
            @PathVariable Long id) {
        Optional<Matricula> matricula = matriculas.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();

        if (matricula.isEmpty()) {
            java.util.Map<String, Object> erro = new java.util.LinkedHashMap<>();
            erro.put("type", "https://gestao.academico/probs/matricula-nao-encontrada");
            erro.put("title", "Matrícula não encontrada");
            erro.put("status", 404);
            erro.put("detail", "Não existe matrícula com id " + id);
            erro.put("instance", "/api/v1/matriculas/" + id);
            return ResponseEntity.status(404).body(erro);
        }

        return ResponseEntity.ok(matricula.get());
    }

    @Operation(summary = "Cadastrar nova matrícula", description = "Cria um novo registro de matrícula no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Matrícula criada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Matricula.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<Matricula> cadastrar(@RequestBody Matricula novaMatricula) {
        long novoId = matriculas.stream()
                .mapToLong(Matricula::getId)
                .max()
                .orElse(0) + 1;
        novaMatricula.setId(novoId);
        matriculas.add(novaMatricula);
        return ResponseEntity.status(201).body(novaMatricula);
    }

    @Operation(summary = "Atualizar matrícula", description = "Atualiza os dados de uma matrícula existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Matrícula atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Matrícula não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(
            @Parameter(description = "ID da matrícula a ser atualizada", example = "1")
            @PathVariable Long id,
            @RequestBody Matricula matriculaAtualizada) {
        for (Matricula matricula : matriculas) {
            if (matricula.getId().equals(id)) {
                matricula.setAlunoId(matriculaAtualizada.getAlunoId());
                matricula.setDisciplinaId(matriculaAtualizada.getDisciplinaId());
                matricula.setDataMatricula(matriculaAtualizada.getDataMatricula());
                matricula.setStatus(matriculaAtualizada.getStatus());
                return ResponseEntity.noContent().build();
            }
        }
        java.util.Map<String, Object> erro = new java.util.LinkedHashMap<>();
        erro.put("type", "https://gestao.academico/probs/matricula-nao-encontrada");
        erro.put("title", "Matrícula não encontrada");
        erro.put("status", 404);
        erro.put("detail", "Não existe matrícula com id " + id);
        erro.put("instance", "/api/v1/matriculas/" + id);
        return ResponseEntity.status(404).body(erro);
    }

    @Operation(summary = "Remover matrícula", description = "Remove uma matrícula do sistema com base no ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Matrícula removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Matrícula não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remover(
            @Parameter(description = "ID da matrícula a ser removida", example = "1")
            @PathVariable Long id) {
        boolean removido = matriculas.removeIf(m -> m.getId().equals(id));

        if (!removido) {
            java.util.Map<String, Object> erro = new java.util.LinkedHashMap<>();
            erro.put("type", "https://gestao.academico/probs/matricula-nao-encontrada");
            erro.put("title", "Matrícula não encontrada");
            erro.put("status", 404);
            erro.put("detail", "Não existe matrícula com id " + id);
            erro.put("instance", "/api/v1/matriculas/" + id);
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
