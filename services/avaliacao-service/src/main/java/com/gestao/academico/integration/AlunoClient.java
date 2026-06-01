package com.gestao.academico.integration;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
public class AlunoClient {

    private final RestTemplate restTemplate;
    private final Executor executor;
    private final String baseUrl;

    public AlunoClient(
            RestTemplate restTemplate,
            @Qualifier("resilienceExecutor") Executor executor,
            @Value("${services.aluno.base-url}") String baseUrl
    ) {
        this.restTemplate = restTemplate;
        this.executor = executor;
        this.baseUrl = baseUrl;
    }

    @CircuitBreaker(name = "aluno-service", fallbackMethod = "alunoFallback")
    @Retry(name = "aluno-service")
    @TimeLimiter(name = "aluno-service")
    public CompletableFuture<Boolean> alunoExiste(Long alunoId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                ResponseEntity<Object> response = restTemplate.getForEntity(baseUrl + "/" + alunoId, Object.class);
                return response.getStatusCode().is2xxSuccessful();
            } catch (HttpClientErrorException.NotFound ex) {
                return false;
            }
        }, executor);
    }

    public CompletableFuture<Boolean> alunoFallback(Long alunoId, Throwable ex) {
        return CompletableFuture.completedFuture(false);
    }
}
