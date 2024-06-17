package com.flight.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FlightSearchService {

	public static void main(String[] args) {
		SpringApplication.run(FlightSearchService.class, args);
	}

}
