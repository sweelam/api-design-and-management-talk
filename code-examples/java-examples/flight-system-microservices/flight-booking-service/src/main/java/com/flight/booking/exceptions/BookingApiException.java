package com.flight.booking.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookingApiException extends ResponseStatusException {
    public BookingApiException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public BookingApiException(String reason, HttpStatus status) {
        super(status, reason);
    }
}
