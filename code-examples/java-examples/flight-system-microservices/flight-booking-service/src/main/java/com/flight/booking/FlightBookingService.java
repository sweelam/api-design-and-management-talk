package com.flight.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FlightBookingService {

	public static void main(String[] args) {
		SpringApplication.run(FlightBookingService.class, args);
	}

}
