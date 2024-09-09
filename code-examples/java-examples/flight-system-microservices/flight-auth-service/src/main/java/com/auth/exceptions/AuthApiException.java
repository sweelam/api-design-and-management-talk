package com.auth.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuthApiException extends ResponseStatusException {
    public AuthApiException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public AuthApiException(String reason, HttpStatus status) {
        super(status, reason);
    }
}
