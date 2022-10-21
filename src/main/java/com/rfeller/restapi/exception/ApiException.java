package com.rfeller.restapi.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
