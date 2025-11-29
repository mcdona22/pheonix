package com.mcdona22.pheonix.common;

import jakarta.persistence.PersistenceException;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    Logger logger = LoggerFactory.getLogger(this.getClass());


    @ExceptionHandler(PersistenceException.class)
    protected ResponseEntity<@NonNull ApiError> handleEntityNotFound(PersistenceException ex,
                                                                     WebRequest request) {
        logger.info("We entered this handler");
        final var error =
                new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), "");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<@NonNull ApiError> handleRuntimeException(RuntimeException ex,
                                                                       WebRequest request) {
        logger.info("Runtime Exception encountered: {}", ex.getMessage());
        final var error =
                new ApiError(HttpStatus.BAD_REQUEST, "Unspecified Error", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(PheonixRuntimeException.class)
    protected ResponseEntity<@NonNull ApiError> handleRuntimeException(PheonixRuntimeException ex,
                                                                       WebRequest request) {
        final var error = ex.getError();
        logger.info("Pheonix Runtime Exception encountered: {}", error.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

}
