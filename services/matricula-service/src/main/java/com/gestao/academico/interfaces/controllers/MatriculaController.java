package com.gestao.academico.interfaces.controllers;

import com.gestao.academico.domain.entities.Matricula;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

    private final com.gestao.academico.domain.entities.MatriculaRepository matriculaRepository;

    public MatriculaController(com.gestao.academico.domain.entities.MatriculaRepository matriculaRepository) {
        this.matriculaRepository = matriculaRepository;
    }


    @Operation(summary = "Listar todas as matrículas", description = "Retorna uma lista com todas as matrículas cadastradas")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista de matrículas retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Matricula.class)))
    @GetMapping
    public List<Matricula> listarTodos() {
        return matriculaRepository.findAll();
    }


    @Operation(summary = "Buscar matrícula por ID", description = "Retorna uma matrícula específica com base no identificador fornecido")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Matrícula encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Matricula.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Matrícula não encontrada",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(
            @Parameter(description = "ID da matrícula a ser buscada", example = "1")
            @PathVariable Long id) {

        Optional<Matricula> matricula = matriculaRepository.findById(id);

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
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Matrícula criada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Matricula.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<Matricula> cadastrar(@RequestBody Matricula novaMatricula) {
        novaMatricula.setId(null);
        Matricula saved = matriculaRepository.save(novaMatricula);
        return ResponseEntity.status(201).body(saved);
    }


    @Operation(summary = "Atualizar matrícula", description = "Atualiza os dados de uma matrícula existente")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Matrícula atualizada com sucesso"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Matrícula não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(
            @Parameter(description = "ID da matrícula a ser atualizada", example = "1")
            @PathVariable Long id,
            @RequestBody Matricula matriculaAtualizada) {

        Optional<Matricula> existente = matriculaRepository.findById(id);
        if (existente.isEmpty()) {
            java.util.Map<String, Object> erro = new java.util.LinkedHashMap<>();
            erro.put("type", "https://gestao.academico/probs/matricula-nao-encontrada");
            erro.put("title", "Matrícula não encontrada");
            erro.put("status", 404);
            erro.put("detail", "Não existe matrícula com id " + id);
            erro.put("instance", "/api/v1/matriculas/" + id);
            return ResponseEntity.status(404).body(erro);
        }

        Matricula matricula = existente.get();
        matricula.setAlunoId(matriculaAtualizada.getAlunoId());
        matricula.setDisciplinaId(matriculaAtualizada.getDisciplinaId());
        matricula.setDataMatricula(matriculaAtualizada.getDataMatricula());
        matricula.setStatus(matriculaAtualizada.getStatus());
        matriculaRepository.save(matricula);

        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Remover matrícula", description = "Remove uma matrícula do sistema com base no ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Matrícula removida com sucesso"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Matrícula não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remover(
            @Parameter(description = "ID da matrícula a ser removida", example = "1")
            @PathVariable Long id) {

        Optional<Matricula> existente = matriculaRepository.findById(id);
        if (existente.isEmpty()) {
            java.util.Map<String, Object> erro = new java.util.LinkedHashMap<>();
            erro.put("type", "https://gestao.academico/probs/matricula-nao-encontrada");
            erro.put("title", "Matrícula não encontrada");
            erro.put("status", 404);
            erro.put("detail", "Não existe matrícula com id " + id);
            erro.put("instance", "/api/v1/matriculas/" + id);
            return ResponseEntity.status(404).body(erro);
        }

        matriculaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Verificar status da API", description = "Retorna informações sobre a versão e status da API")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Status retornado com sucesso")
    @GetMapping("/status")
    public String checkStatus() {
        return "{\"version\": \"v1\", \"status\": \"Online\", \"architecture\": \"Clean Architecture\"}";
    }
}
