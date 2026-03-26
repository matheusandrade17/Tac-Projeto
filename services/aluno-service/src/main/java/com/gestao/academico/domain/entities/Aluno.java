package com.gestao.academico.interfaces.controllers;

import com.gestao.academico.domain.entities.Aluno;
import com.gestao.academico.domain.repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository repository;

    @GetMapping
    public List<Aluno> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(aluno -> ResponseEntity.ok((Object) aluno))
                .orElseGet(() -> ResponseEntity.status(404).body(gerarErro404(id)));
    }

    @PostMapping
    public ResponseEntity<Aluno> cadastrar(@RequestBody Aluno novoAluno) {
        // O JPA ignora o ID enviado e gera um novo automaticamente
        Aluno alunoSalvo = repository.save(novoAluno);
        return ResponseEntity.status(201).body(alunoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody Aluno dadosAtualizados) {
        return repository.findById(id)
                .map(alunoExistente -> {
                    alunoExistente.setNome(dadosAtualizados.getNome());
                    alunoExistente.setEmail(dadosAtualizados.getEmail());
                    repository.save(alunoExistente);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.status(404).body(gerarErro404(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remover(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(404).body(gerarErro404(id));
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    public String checkStatus() {
        return "{\"version\": \"v1\", \"status\": \"Online\", \"architecture\": \"Clean Architecture\"}";
    }

    // Método auxiliar para manter seu padrão de erro customizado
    private Map<String, Object> gerarErro404(Long id) {
        Map<String, Object> erro = new LinkedHashMap<>();
        erro.put("type", "https://gestao.academico/probs/aluno-nao-encontrado");
        erro.put("title", "Aluno não encontrado");
        erro.put("status", 404);
        erro.put("detail", "Não existe aluno com id " + id);
        erro.put("instance", "/api/v1/alunos/" + id);
        return erro;
    }
}