package com.users.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.AbstractMap;

@ControllerAdvice
@Component
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler
  public ResponseEntity<AbstractMap.SimpleEntry<String, String>> handle(Exception exception) {
    LOG.error("Request could not be processed: ", exception);
    AbstractMap.SimpleEntry<String, String> response =
        new AbstractMap.SimpleEntry<>("message", "Request could not be processed");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }


  @ExceptionHandler(value = {UserApiException.class})
  public ResponseEntity<AbstractMap.SimpleEntry<String, String>> handle(UserApiException exception) {
    LOG.error("Request could not be processed: ", exception);

    AbstractMap.SimpleEntry<String, String> response =
            new AbstractMap.SimpleEntry<>("message", exception.getReason());

    return ResponseEntity.status(exception.getStatusCode()).body(response);
  }

  protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    LOG.error("Request could not be processed: ", ex);

    AbstractMap.SimpleEntry<String, String> response =
            new AbstractMap.SimpleEntry<>("message", ex.getVariableName() + " is required! ");

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }
}
