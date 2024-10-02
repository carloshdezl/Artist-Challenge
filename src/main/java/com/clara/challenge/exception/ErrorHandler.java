package com.clara.challenge.exception;

import com.clara.challenge.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
    private static final String DISCOGS_API_ERROR = "20000";
    private static final String NOT_FOUND = "40000";

    @ExceptionHandler(IntegrationException.class)
    private ResponseEntity<ErrorResponse> handleIntegrationException(
            IntegrationException exception) {
        log.error("handleIntegrationException error - Exception: ", exception);
        return new ResponseEntity<>(ErrorResponse.builder()
                .message(exception.getMessage())
                .code(DISCOGS_API_ERROR)
                .build(), exception.getHttpStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorResponse> handleNotFoundException(
            NotFoundException exception) {
        log.error("handleNotFoundException error - Exception: ", exception);
        return new ResponseEntity<>(ErrorResponse.builder()
                .message(exception.getMessage())
                .code(NOT_FOUND)
                .build(), exception.getHttpStatus());
    }
}
