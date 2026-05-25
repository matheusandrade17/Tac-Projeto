package com.gestao.academico.observability.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import java.time.Duration;
import java.util.Objects;

public class BusinessMatriculaMetrics {

    private static final String METRIC_MATRICULAS_TOTAL = "matriculas_total";
    private static final String METRIC_CACHE_FAILURES_TOTAL = "cache_failures_total";

    private final Counter matriculasCounterSuccess;
    private final Counter matriculasCounterFailure;

    private final Counter cacheFailuresCounter;

    public BusinessMatriculaMetrics(MeterRegistry meterRegistry) {
        Objects.requireNonNull(meterRegistry, "meterRegistry");

        this.matriculasCounterSuccess = Counter.builder(METRIC_MATRICULAS_TOTAL)
                .description("Total de matrículas processadas com sucesso")
                .tag("resultado", "sucesso")
                .register(meterRegistry);

        this.matriculasCounterFailure = Counter.builder(METRIC_MATRICULAS_TOTAL)
                .description("Total de matrículas processadas com falha")
                .tag("resultado", "falha")
                .register(meterRegistry);

        this.cacheFailuresCounter = Counter.builder(METRIC_CACHE_FAILURES_TOTAL)
                .description("Total de falhas ao interagir com cache (Redis)")
                .tag("cache", "redis")
                .register(meterRegistry);
    }

    public void recordMatriculaSuccess() {
        matriculasCounterSuccess.increment();
    }

    public void recordMatriculaFailure() {
        matriculasCounterFailure.increment();
    }

    public void recordCacheFailure() {
        cacheFailuresCounter.increment();
    }

    public void recordCacheFailureWithReason(String reason) {
        // Mantemos tags simples para evitar cardinalidade alta.
        // Caso queira expandir por motivo, habilite uma tag fixa/baixa cardinalidade.
        cacheFailuresCounter.increment();
    }
}