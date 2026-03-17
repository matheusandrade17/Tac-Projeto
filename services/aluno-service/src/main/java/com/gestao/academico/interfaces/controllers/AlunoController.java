package com.gestao.academico.interfaces.controllers;

import com.gestao.academico.domain.entities.Aluno;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/alunos")
public class AlunoController {

    @GetMapping
    public List<Aluno> listarTodos() {
        return Arrays.asList(
            new Aluno(1L, "Pedro Oliveira", "2026001"),
            new Aluno(2L, "Matheus Andrade", "2026002")
        );
    }

    @GetMapping("/status")
    public String checkStatus() {
        return "{\"version\": \"v1\", \"status\": \"Online\", \"architecture\": \"Clean Architecture\"}";
    }
}
