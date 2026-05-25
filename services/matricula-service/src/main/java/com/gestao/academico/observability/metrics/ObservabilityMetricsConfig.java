package com.gestao.academico.observability.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObservabilityMetricsConfig {

    @Bean
    public BusinessMatriculaMetrics businessMatriculaMetrics(MeterRegistry meterRegistry) {
        return new BusinessMatriculaMetrics(meterRegistry);
    }

    @Bean
    public RequestProcessingTimer requestProcessingTimer(MeterRegistry meterRegistry) {
        return new RequestProcessingTimer(meterRegistry);
    }
}