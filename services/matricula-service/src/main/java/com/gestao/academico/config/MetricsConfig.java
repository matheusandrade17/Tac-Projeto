package com.gestao.academico.config;

import com.gestao.academico.observability.metrics.BusinessMatriculaMetrics;
import com.gestao.academico.observability.metrics.RequestProcessingTimer;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;

public class MetricsConfig {

    @Bean
    public BusinessMatriculaMetrics businessMatriculaMetrics(MeterRegistry meterRegistry) {
        return new BusinessMatriculaMetrics(meterRegistry);
    }

    @Bean
    public RequestProcessingTimer requestProcessingTimer(MeterRegistry meterRegistry) {
        return new RequestProcessingTimer(meterRegistry);
    }
}