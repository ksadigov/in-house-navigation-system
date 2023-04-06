package com.sadigov.navigationsystem.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {MobileStationNotFoundException.class, BaseStationNotFoundException.class})
    public Map<String, String> handleHttpNotFound(Exception e) {
        log.error("Not Found", e);
        return buildErrorResponseBody(e);
    }

    private Map<String, String> buildErrorResponseBody(Exception e) {
        final Map<String, String> errors = new HashMap<>();
        errors.put("errorMessage", e.getMessage());
        return errors;
    }
}
