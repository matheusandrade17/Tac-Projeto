package com.gestao.academico.interfaces.controllers;

import com.gestao.academico.domain.entities.Aluno;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/alunos")
public class AlunoController {

    private static List<Aluno> alunos = new ArrayList<>();

    @GetMapping
    public List<Aluno> listarTodos() {
        return alunos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {
        Optional<Aluno> aluno = alunos.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();

        if (aluno.isEmpty()) {
            return ResponseEntity.status(404).body(
                    new java.util.LinkedHashMap<String, Object>() {{
                        put("type", "https://gestao.academico/probs/aluno-nao-encontrado");
                        put("title", "Aluno não encontrado");
                        put("status", 404);
                        put("detail", "Não existe aluno com id " + id);
                        put("instance", "/api/v1/alunos/" + id);
                    }}
            );
        }

        return ResponseEntity.ok(aluno.get());
    }

    @PostMapping
    public ResponseEntity<Aluno> cadastrar(@RequestBody Aluno novoAluno) {
        alunos.add(novoAluno);
        return ResponseEntity.status(201).body(novoAluno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody Aluno alunoAtualizado) {
        for (Aluno aluno : alunos) {
            if (aluno.getId().equals(id)) {
                aluno.setNome(alunoAtualizado.getNome());
                aluno.setMatricula(alunoAtualizado.getMatricula());
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.status(404).body(
                new java.util.LinkedHashMap<String, Object>() {{
                    put("type", "https://gestao.academico/probs/aluno-nao-encontrado");
                    put("title", "Aluno não encontrado");
                    put("status", 404);
                    put("detail", "Não existe aluno com id " + id);
                    put("instance", "/api/v1/alunos/" + id);
                }}
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remover(@PathVariable Long id) {
        boolean removido = alunos.removeIf(a -> a.getId().equals(id));

        if (!removido) {
            return ResponseEntity.status(404).body(
                    new java.util.LinkedHashMap<String, Object>() {{
                        put("type", "https://gestao.academico/probs/aluno-nao-encontrado");
                        put("title", "Aluno não encontrado");
                        put("status", 404);
                        put("detail", "Não existe aluno com id " + id);
                        put("instance", "/api/v1/alunos/" + id);
                    }}
            );
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    public String checkStatus() {
        return "{\"version\": \"v1\", \"status\": \"Online\", \"architecture\": \"Clean Architecture\"}";
    }
}