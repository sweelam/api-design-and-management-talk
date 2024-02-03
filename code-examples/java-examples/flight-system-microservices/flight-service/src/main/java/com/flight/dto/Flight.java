package com.flight.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public record Flight(UUID id, String flightName, Instant time, String customerEmail) implements Serializable {
}
