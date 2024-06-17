package com.flight.search.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FlightApiException extends ResponseStatusException {
    public FlightApiException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public FlightApiException(String reason, HttpStatus status) {
        super(status, reason);
    }
}
