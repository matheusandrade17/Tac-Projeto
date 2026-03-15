package com.gestao.academico.interfaces.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/alunos")
public class HealthCheckController {
    @GetMapping("/status")
    public Map<String, String> status() {
        return Map.of(
            "status", "Online",
            "version", "v1",
            "architecture", "Clean Architecture"
        );
    }
}
