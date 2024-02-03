package com.customers.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomerApiException  extends ResponseStatusException {
    public CustomerApiException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public CustomerApiException(String reason, HttpStatus status) {
        super(status, reason);
    }
}
