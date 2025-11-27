package com.mcdona22.pheonix.common;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
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

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<@NonNull ApiError> handleEntityNotFound(EntityNotFoundException ex,
                                                                     WebRequest request) {
        final var error =
                new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getContextPath().);
        return ResponseEntity.badRequest().body(error);
    }
}
