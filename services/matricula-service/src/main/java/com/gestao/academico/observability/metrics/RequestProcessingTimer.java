package com.gestao.academico.observability.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

import java.util.Objects;

public class RequestProcessingTimer {

    private static final String METRIC_PROCESSING_DURATION_SECONDS = "matricula_processing_duration_seconds";

    private final Timer timer;

    public RequestProcessingTimer(MeterRegistry meterRegistry) {
        Objects.requireNonNull(meterRegistry, "meterRegistry");

        this.timer = Timer.builder(METRIC_PROCESSING_DURATION_SECONDS)
                .description("Tempo de processamento de operações relacionadas a matrícula")
                .publishPercentileHistogram(true)
                .publishPercentiles(0.5, 0.95)
                .register(meterRegistry);
    }

    public void recordProcessing(Runnable runnable) {
        timer.record(runnable);
    }

    public <T> T recordProcessing(java.util.concurrent.Callable<T> callable) throws Exception {
        return timer.recordCallable(callable);
    }
}