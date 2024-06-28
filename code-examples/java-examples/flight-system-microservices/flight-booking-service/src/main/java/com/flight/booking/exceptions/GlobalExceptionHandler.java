package com.flight.booking.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.AbstractMap;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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


  @ExceptionHandler(value = {BookingApiException.class})
  public ResponseEntity<AbstractMap.SimpleEntry<String, String>> handle(BookingApiException exception) {
    LOG.error("Request could not be processed: ", exception);

    AbstractMap.SimpleEntry<String, String> response =
            new AbstractMap.SimpleEntry<>("message", exception.getReason());

    return ResponseEntity.status(exception.getStatusCode()).body(response);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException exception,
                                                                        HttpHeaders headers, HttpStatusCode status,
                                                                        WebRequest request) {
    LOG.error("Request could not be processed: {}", exception.getLocalizedMessage());

    BookingApiException bookingApiException =
            new BookingApiException(exception.getLocalizedMessage(), HttpStatus.resolve(status.value()));

    return ResponseEntity.status(exception.getStatusCode()).body(bookingApiException);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers,
                                                                HttpStatusCode status, WebRequest request) {
    LOG.error("Request could not be processed: {}", exception.getLocalizedMessage());

    BookingApiException bookingApiException =
            new BookingApiException(exception.getLocalizedMessage(), HttpStatus.resolve(status.value()));

    return ResponseEntity.status(exception.getStatusCode()).body(bookingApiException);
  }

  @Override
  protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException exception,
                                                                          HttpHeaders headers, HttpStatusCode status,
                                                                          WebRequest request) {
    LOG.error("Request could not be processed: {}" , exception.getLocalizedMessage());

    BookingApiException bookingApiException =
            new BookingApiException(exception.getLocalizedMessage(), HttpStatus.resolve(status.value()));

    return ResponseEntity.status(exception.getStatusCode()).body(bookingApiException);
  }
}
