package com.gestao.academico.interfaces.controllers;

import com.gestao.academico.domain.entities.Aluno;
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
@RequestMapping("/api/v1/alunos")
@Tag(name = "Alunos", description = "Endpoints para gerenciamento de alunos")
public class AlunoController {

    private static List<Aluno> alunos = new ArrayList<>();

    @Operation(summary = "Listar todos os alunos", description = "Retorna uma lista com todos os alunos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de alunos retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Aluno.class)))
    @GetMapping
    public List<Aluno> listarTodos() {
        return alunos;
    }

    @Operation(summary = "Buscar aluno por ID", description = "Retorna um aluno específico com base no identificador fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aluno.class))),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(


            @Parameter(description = "ID do aluno a ser buscado", example = "1")
            @PathVariable Long id) {
        // Exemplo de cache Redis (proof of concept):
        // como o controller atual usa uma lista in-memory, mantemos o fluxo original.
        // O endpoint cacheado será validado via método cacheado em outro lugar.
        // Mantém a lista como fonte de verdade (in-memory)
        Optional<Aluno> aluno = alunos.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();

        if (aluno.isEmpty()) {
            java.util.Map<String, Object> erro = new java.util.LinkedHashMap<>();
            erro.put("type", "https://gestao.academico/probs/aluno-nao-encontrado");
            erro.put("title", "Aluno não encontrado");
            erro.put("status", 404);
            erro.put("detail", "Não existe aluno com id " + id);
            erro.put("instance", "/api/v1/alunos/" + id);
            return ResponseEntity.status(404).body(erro);
        }

        return ResponseEntity.ok(aluno.get());
    }

    @Operation(summary = "Cadastrar novo aluno", description = "Cria um novo registro de aluno no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aluno criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aluno.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<Aluno> cadastrar(@RequestBody Aluno novoAluno) {
        // Gera ID automático para novos alunos
        long novoId = alunos.stream()
                .mapToLong(Aluno::getId)
                .max()
                .orElse(0) + 1;
        novoAluno.setId(novoId);
        alunos.add(novoAluno);
        return ResponseEntity.status(201).body(novoAluno);
    }

    @Operation(summary = "Atualizar aluno", description = "Atualiza os dados de um aluno existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aluno atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(
            @Parameter(description = "ID do aluno a ser atualizado", example = "1")
            @PathVariable Long id,
            @RequestBody Aluno alunoAtualizado) {
        for (Aluno aluno : alunos) {
            if (aluno.getId().equals(id)) {
                aluno.setNome(alunoAtualizado.getNome());
                aluno.setEmail(alunoAtualizado.getEmail());
                return ResponseEntity.noContent().build();
            }
        }
        java.util.Map<String, Object> erro = new java.util.LinkedHashMap<>();
        erro.put("type", "https://gestao.academico/probs/aluno-nao-encontrado");
        erro.put("title", "Aluno não encontrado");
        erro.put("status", 404);
        erro.put("detail", "Não existe aluno com id " + id);
        erro.put("instance", "/api/v1/alunos/" + id);
        return ResponseEntity.status(404).body(erro);
    }

    @Operation(summary = "Remover aluno", description = "Remove um aluno do sistema com base no ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aluno removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remover(
            @Parameter(description = "ID do aluno a ser removido", example = "1")
            @PathVariable Long id) {
        boolean removido = alunos.removeIf(a -> a.getId().equals(id));

        if (!removido) {
            java.util.Map<String, Object> erro = new java.util.LinkedHashMap<>();
            erro.put("type", "https://gestao.academico/probs/aluno-nao-encontrado");
            erro.put("title", "Aluno não encontrado");
            erro.put("status", 404);
            erro.put("detail", "Não existe aluno com id " + id);
            erro.put("instance", "/api/v1/alunos/" + id);
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
