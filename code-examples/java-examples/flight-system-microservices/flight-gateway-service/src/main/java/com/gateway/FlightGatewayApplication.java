package com.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class FlightGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightGatewayApplication.class, args);
	}

	@Bean
	KeyResolver userKeyResolver() {
		return exchange -> Mono.just("1");
	}

}
