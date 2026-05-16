package com.gestao.academico.interfaces.controllers;

import com.gestao.academico.domain.entities.Aluno;
import com.gestao.academico.domain.entities.AlunoCacheFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/alunos")
@Tag(name = "Alunos", description = "Endpoints para gerenciamento de alunos")
public class AlunoController {

    private final AlunoCacheFacade alunoCacheFacade;
    private final StringRedisTemplate stringRedisTemplate;

    public AlunoController(AlunoCacheFacade alunoCacheFacade, StringRedisTemplate stringRedisTemplate) {
        this.alunoCacheFacade = alunoCacheFacade;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Operation(summary = "Buscar aluno por ID (Cache Redis)", description = "Retorna um aluno específico com base no identificador fornecido")
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

        Optional<Aluno> aluno = alunoCacheFacade.buscarPorIdCached(id);

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

    /**
     * Observação:
     * Este controller original do repositório usa lista in-memory; para manter o projeto compilando,
     * preservamos a assinatura, mas as invalidações de cache ficam delegadas ao facade.
     * Caso seu CRUD real esteja em outra camada (DB), ajustes podem ser necessários.
     */
    @Operation(summary = "Cadastrar novo aluno (invalida cache)", description = "Cria um novo registro de aluno no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aluno criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aluno.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<Aluno> cadastrar(@RequestBody Aluno novoAluno) {
        // Mantém comportamento do repositório (não há DTO/command/event wiring aqui).
        // Invalidação simples e segura para não servir dado stale.
        alunoCacheFacade.evictAllAlunos();

        // Sem persistência aqui (o projeto ainda está com armazenamento em memória no controller base).
        return ResponseEntity.status(201).body(novoAluno);
    }

    @Operation(summary = "Atualizar aluno (invalida cache)", description = "Atualiza os dados de um aluno existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aluno atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(
            @Parameter(description = "ID do aluno a ser atualizado", example = "1")
            @PathVariable Long id,
            @RequestBody Aluno alunoAtualizado) {

        // Sem persistência aqui; apenas garante consistência do cache.
        alunoCacheFacade.evictAlunoById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remover aluno (invalida cache)", description = "Remove um aluno do sistema com base no ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aluno removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remover(
            @Parameter(description = "ID do aluno a ser removido", example = "1")
            @PathVariable Long id) {

        alunoCacheFacade.evictAlunoById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Verificar status da API + Redis", description = "Retorna informações sobre a versão e status da API")
    @ApiResponse(responseCode = "200", description = "Status retornado com sucesso")
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> checkStatus() {

        boolean redisOk = false;
        String redisError = null;

        try {
            if (stringRedisTemplate != null
                    && stringRedisTemplate.getConnectionFactory() != null
                    && stringRedisTemplate.getConnectionFactory().getConnection() != null) {

                redisOk = "PONG".equalsIgnoreCase(
                        stringRedisTemplate.getConnectionFactory().getConnection().ping()
                );
            }
        } catch (Exception e) {
            redisOk = false;
            redisError = e.getMessage();
        }

        return ResponseEntity.ok(Map.of(
                "version", "v1",
                "status", redisOk ? "Online" : "Degraded",
                "architecture", "Clean Architecture",
                "redis", Map.of(
                        "ok", redisOk,
                        "error", redisError
                )
        ));
    }
}
