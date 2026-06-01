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
public class DisciplinaClient {

    private final RestTemplate restTemplate;
    private final Executor executor;
    private final String baseUrl;

    public DisciplinaClient(
            RestTemplate restTemplate,
            @Qualifier("resilienceExecutor") Executor executor,
            @Value("${services.disciplina.base-url}") String baseUrl
    ) {
        this.restTemplate = restTemplate;
        this.executor = executor;
        this.baseUrl = baseUrl;
    }

    @CircuitBreaker(name = "disciplina-service", fallbackMethod = "disciplinaFallback")
    @Retry(name = "disciplina-service")
    @TimeLimiter(name = "disciplina-service")
    public CompletableFuture<Boolean> disciplinaExiste(Long disciplinaId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                ResponseEntity<Object> response = restTemplate.getForEntity(baseUrl + "/" + disciplinaId, Object.class);
                return response.getStatusCode().is2xxSuccessful();
            } catch (HttpClientErrorException.NotFound ex) {
                return false;
            }
        }, executor);
    }

    public CompletableFuture<Boolean> disciplinaFallback(Long disciplinaId, Throwable ex) {
        return CompletableFuture.completedFuture(false);
    }
}
