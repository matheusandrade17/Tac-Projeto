package com.gestao.academico.interfaces.controllers;

import com.gestao.academico.domain.entities.Avaliacao;
import com.gestao.academico.domain.entities.AvaliacaoRepository;
import com.gestao.academico.domain.entities.AvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/avaliacoes")
@Tag(name = "Avaliacoes", description = "Endpoints para gerenciamento de avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoRepository avaliacaoRepository, AvaliacaoService avaliacaoService) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.avaliacaoService = avaliacaoService;
    }

    @Operation(summary = "Listar todas as avaliacoes", description = "Retorna uma lista com todas as avaliacoes cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de avaliacoes retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Avaliacao.class)))
    @GetMapping
    public List<Avaliacao> listarTodos() {
        return avaliacaoRepository.findAll();
    }

    @Operation(summary = "Buscar avaliacao por ID", description = "Retorna uma avaliacao especifica com base no identificador fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliacao encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Avaliacao.class))),
            @ApiResponse(responseCode = "404", description = "Avaliacao nao encontrada",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(
            @Parameter(description = "ID da avaliacao a ser buscada", example = "1")
            @PathVariable Long id) {
        Optional<Avaliacao> avaliacao = avaliacaoRepository.findById(id);

        if (avaliacao.isEmpty()) {
            return ResponseEntity.status(404).body(erroNaoEncontrada(id));
        }

        return ResponseEntity.ok(avaliacao.get());
    }

    @Operation(summary = "Cadastrar nova avaliacao", description = "Cria um novo registro de avaliacao no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Avaliacao criada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Avaliacao.class))),
            @ApiResponse(responseCode = "400", description = "Dados invalidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<Avaliacao> cadastrar(@RequestBody Avaliacao novaAvaliacao) {
        novaAvaliacao.setId(null);
        Avaliacao saved = avaliacaoService.salvar(novaAvaliacao);
        return ResponseEntity.status(201).body(saved);
    }

    @Operation(summary = "Atualizar avaliacao", description = "Atualiza os dados de uma avaliacao existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Avaliacao atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Avaliacao nao encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(
            @Parameter(description = "ID da avaliacao a ser atualizada", example = "1")
            @PathVariable Long id,
            @RequestBody Avaliacao avaliacaoAtualizada) {
        Optional<Avaliacao> existente = avaliacaoRepository.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(404).body(erroNaoEncontrada(id));
        }

        Avaliacao avaliacao = existente.get();
        avaliacao.setDisciplinaId(avaliacaoAtualizada.getDisciplinaId());
        avaliacao.setAlunoId(avaliacaoAtualizada.getAlunoId());
        avaliacao.setTipo(avaliacaoAtualizada.getTipo());
        avaliacao.setNota(avaliacaoAtualizada.getNota());
        avaliacao.setDataAvaliacao(avaliacaoAtualizada.getDataAvaliacao());
        avaliacaoRepository.save(avaliacao);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remover avaliacao", description = "Remove uma avaliacao do sistema com base no ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Avaliacao removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Avaliacao nao encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remover(
            @Parameter(description = "ID da avaliacao a ser removida", example = "1")
            @PathVariable Long id) {
        if (!avaliacaoRepository.existsById(id)) {
            return ResponseEntity.status(404).body(erroNaoEncontrada(id));
        }

        avaliacaoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Verificar status da API", description = "Retorna informacoes sobre a versao e status da API")
    @ApiResponse(responseCode = "200", description = "Status retornado com sucesso")
    @GetMapping("/status")
    public String checkStatus() {
        return "{\"version\": \"v1\", \"status\": \"Online\", \"architecture\": \"Clean Architecture\"}";
    }

    private java.util.Map<String, Object> erroNaoEncontrada(Long id) {
        java.util.Map<String, Object> erro = new java.util.LinkedHashMap<>();
        erro.put("type", "https://gestao.academico/probs/avaliacao-nao-encontrada");
        erro.put("title", "Avaliacao nao encontrada");
        erro.put("status", 404);
        erro.put("detail", "Nao existe avaliacao com id " + id);
        erro.put("instance", "/api/v1/avaliacoes/" + id);
        return erro;
    }
}
