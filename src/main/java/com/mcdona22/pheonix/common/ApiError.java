package com.mcdona22.pheonix.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
public class ApiError {
    @Getter
    final private HttpStatus status;
    @Getter
    final private LocalDateTime timestamp = LocalDateTime.now();
    @Getter
    private String message;
    @Getter
    private String debugMessage;
}
