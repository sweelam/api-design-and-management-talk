package com.users.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(value = "opentracing.jaeger.enabled", havingValue = "false", matchIfMissing = false)
@Configuration
public class TracingConfig {
    @Bean
    public io.opentracing.Tracer jaegerTracer() {
        return io.opentracing.noop.NoopTracerFactory.create();
    }
}
