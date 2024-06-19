package com.flight.search.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public record FlightDto(
        Integer id,
        String flightNumber,
        String departureAirport,
        String arrivalAirport,
        Instant departureTime,
        Instant arrivalTime,
        BigDecimal price) implements Serializable {}
