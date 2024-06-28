package com.users.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserApiException extends ResponseStatusException {
    public UserApiException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public UserApiException(String reason, HttpStatus status) {
        super(status, reason);
    }
}
