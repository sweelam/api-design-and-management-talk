package com.users.api;

import org.springframework.web.context.request.async.DeferredResult;

public class ApiResponseUtils<T> {
    private ApiResponseUtils() {}

    public static <T> DeferredResult<T> toResponse(T input) {
        DeferredResult<T> result = new DeferredResult<>();
        result.setResult(input);
        return result;
    }
}
