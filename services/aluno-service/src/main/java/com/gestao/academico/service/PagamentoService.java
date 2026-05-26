package com.gestao.academico.service;

import com.gestao.academico.dto.PagamentoDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class PagamentoService {

    private final RestTemplate rest;
    private final Executor executor;
    private final String pagamentosUrl;

    public PagamentoService(
            RestTemplate rest,
            @Qualifier("resilienceExecutor") Executor executor,
            @Value("${services.pagamentos.base-url}") String pagamentosUrl
    ) {
        this.rest = rest;
        this.executor = executor;
        this.pagamentosUrl = pagamentosUrl;
    }

    @CircuitBreaker(name = "pagamentos", fallbackMethod = "pagamentosFallbackAsync")
    @Retry(name = "pagamentos")
    @TimeLimiter(name = "pagamentos")
    public CompletableFuture<PagamentoDto> consultarPagamentoAsync(String pedidoId) {
        return CompletableFuture.supplyAsync(() ->
                        rest.getForObject(pagamentosUrl + "/" + pedidoId, PagamentoDto.class),
                executor
        );
    }

    public CompletableFuture<PagamentoDto> pagamentosFallbackAsync(String pedidoId, Throwable ex) {
        return CompletableFuture.completedFuture(PagamentoDto.pending(pedidoId));
    }
}
