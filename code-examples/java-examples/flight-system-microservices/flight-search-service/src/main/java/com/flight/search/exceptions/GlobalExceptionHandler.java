package com.flight.search.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.AbstractMap;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler
  public ResponseEntity<?> handle(Exception exception) {
    LOG.error("Request could not be processed: ", exception);

    if (!exception.getLocalizedMessage().isEmpty()){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getLocalizedMessage());
    }

    AbstractMap.SimpleEntry<String, String> response =
        new AbstractMap.SimpleEntry<>("message", "Request could not be processed");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }


  @ExceptionHandler(value = {FlightApiException.class})
  public ResponseEntity<AbstractMap.SimpleEntry<String, String>> handle(FlightApiException exception) {
    LOG.error("Request could not be processed: ", exception);

    AbstractMap.SimpleEntry<String, String> response =
            new AbstractMap.SimpleEntry<>("message", exception.getReason());

    return ResponseEntity.status(exception.getStatusCode()).body(response);
  }

}
