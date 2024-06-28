package com.flight.booking.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record FlightResponse(Integer id,
                             String flightNumber,
                             String departureAirport,
                             String arrivalAirport,
                             Instant departureTime,
                             Instant arrivalTime,
                             BigDecimal price) {
}
