package com.clara.challenge.exception;

import org.springframework.http.HttpStatus;

public class IntegrationException extends RuntimeException {
    private HttpStatus httpStatus;

    public IntegrationException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
