package com.flight.booking.api;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

public class ApiResponseUtils {
    private ApiResponseUtils() {}

    public static <T> DeferredResult<T> toResponse(T input) {
        DeferredResult<T> result = new DeferredResult<>();
        result.setResult(input);
        return result;
    }

    public static <T> DeferredResult<ResponseEntity<T>> toResponse(T input, HttpStatusCode statusCode) {
        DeferredResult<ResponseEntity<T>> result = new DeferredResult<>();
        result.setResult(new ResponseEntity<>(input, statusCode));
        return result;
    }

    public static <T> DeferredResult<ResponseEntity<T>> toErrorResponse(String error, HttpStatusCode statusCode) {
        DeferredResult<ResponseEntity<T>> result = new DeferredResult<>();

        if (isNotEmpty(error)) {
            result.setErrorResult(ResponseEntity.status(statusCode).body(error));
        }

        return result;
    }
}
