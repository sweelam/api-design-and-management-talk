package com.flight.search.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    OtlpHttpSpanExporter otlpHttpSpanExporter(@Value("${app.tracing.url}") String url) {
        return OtlpHttpSpanExporter.builder()
                .setEndpoint(url)
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Register the Java Time Module to handle Java 8 Date and Time API
        JavaTimeModule module = new JavaTimeModule();

        module.addSerializer(Instant.class, new MyInstantSerializer());

        objectMapper.registerModule(module);
        return objectMapper;
    }

    static class MyInstantSerializer extends JsonSerializer<Instant> {

        private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

        private static final DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).withZone(ZoneId.from(ZoneOffset.UTC));

        @Override
        public void serialize(Instant instant, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(formatter.format(instant));

        }
    }

}
