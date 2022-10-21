package com.rfeller.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity<Object> handleApiException(ApiException exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiExceptionContent apiException = new ApiExceptionContent(
                exception.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(apiException, badRequest);
    }
}
