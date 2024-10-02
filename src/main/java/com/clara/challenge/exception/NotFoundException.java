package com.clara.challenge.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {
    private HttpStatus httpStatus;

    public NotFoundException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
