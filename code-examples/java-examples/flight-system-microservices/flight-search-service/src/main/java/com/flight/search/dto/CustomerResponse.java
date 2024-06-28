package com.flight.search.dto;

import java.io.Serializable;

public record CustomerResponse(Integer id, String name, String email, String flightNumber) implements Serializable {
}
