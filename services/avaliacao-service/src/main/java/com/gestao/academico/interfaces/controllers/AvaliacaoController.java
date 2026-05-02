package com.gestao.academico.interfaces.controllers;

import com.gestao.academico.domain.entities.Avaliacao;
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
@RequestMapping("/api/v1/avaliacoes")
@Tag(name = "Avaliações", description = "Endpoints para gerenciamento de avaliações")
public class AvaliacaoController {

    private static List<Avaliacao> avaliacoes = new ArrayList<>();

    @Operation(summary = "Listar todas as avaliações", description = "Retorna uma lista com todas as avaliações cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de avaliações retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Avaliacao.class)))
    @GetMapping
    public List<Avaliacao> listarTodos() {
        return avaliacoes;
    }

    @Operation(summary = "Buscar avaliação por ID", description = "Retorna uma avaliação específica com base no identificador fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Avaliacao.class))),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(
            @Parameter(description = "ID da avaliação a ser buscada", example = "1")
            @PathVariable Long id) {
        Optional<Avaliacao> avaliacao = avaliacoes.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();

        if (avaliacao.isEmpty()) {
            java.util.Map<String, Object> erro = new java.util.LinkedHashMap<>();
            erro.put("type", "https://gestao.academico/probs/avaliacao-nao-encontrada");
            erro.put("title", "Avaliação não encontrada");
            erro.put("status", 404);
            erro.put("detail", "Não existe avaliação com id " + id);
            erro.put("instance", "/api/v1/avaliacoes/" + id);
            return ResponseEntity.status(404).body(erro);
        }

        return ResponseEntity.ok(avaliacao.get());
    }

    @Operation(summary = "Cadastrar nova avaliação", description = "Cria um novo registro de avaliação no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Avaliacao.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<Avaliacao> cadastrar(@RequestBody Avaliacao novaAvaliacao) {
        long novoId = avaliacoes.stream()
                .mapToLong(Avaliacao::getId)
                .max()
                .orElse(0) + 1;
        novaAvaliacao.setId(novoId);
        avaliacoes.add(novaAvaliacao);
        return ResponseEntity.status(201).body(novaAvaliacao);
    }

    @Operation(summary = "Atualizar avaliação", description = "Atualiza os dados de uma avaliação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Avaliação atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(
            @Parameter(description = "ID da avaliação a ser atualizada", example = "1")
            @PathVariable Long id,
            @RequestBody Avaliacao avaliacaoAtualizada) {
        for (Avaliacao avaliacao : avaliacoes) {
            if (avaliacao.getId().equals(id)) {
                avaliacao.setDisciplinaId(avaliacaoAtualizada.getDisciplinaId());
                avaliacao.setAlunoId(avaliacaoAtualizada.getAlunoId());
                avaliacao.setTipo(avaliacaoAtualizada.getTipo());
                avaliacao.setNota(avaliacaoAtualizada.getNota());
                avaliacao.setDataAvaliacao(avaliacaoAtualizada.getDataAvaliacao());
                return ResponseEntity.noContent().build();
            }
        }
        java.util.Map<String, Object> erro = new java.util.LinkedHashMap<>();
        erro.put("type", "https://gestao.academico/probs/avaliacao-nao-encontrada");
        erro.put("title", "Avaliação não encontrada");
        erro.put("status", 404);
        erro.put("detail", "Não existe avaliação com id " + id);
        erro.put("instance", "/api/v1/avaliacoes/" + id);
        return ResponseEntity.status(404).body(erro);
    }

    @Operation(summary = "Remover avaliação", description = "Remove uma avaliação do sistema com base no ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Avaliação removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remover(
            @Parameter(description = "ID da avaliação a ser removida", example = "1")
            @PathVariable Long id) {
        boolean removido = avaliacoes.removeIf(a -> a.getId().equals(id));

        if (!removido) {
            java.util.Map<String, Object> erro = new java.util.LinkedHashMap<>();
            erro.put("type", "https://gestao.academico/probs/avaliacao-nao-encontrada");
            erro.put("title", "Avaliação não encontrada");
            erro.put("status", 404);
            erro.put("detail", "Não existe avaliação com id " + id);
            erro.put("instance", "/api/v1/avaliacoes/" + id);
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
