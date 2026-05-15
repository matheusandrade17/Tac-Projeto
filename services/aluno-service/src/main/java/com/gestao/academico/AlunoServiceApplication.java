package com.gestao.academico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching

public class AlunoServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlunoServiceApplication.class, args);
    }
}
