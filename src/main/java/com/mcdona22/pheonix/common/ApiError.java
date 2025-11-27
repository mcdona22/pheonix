package com.mcdona22.pheonix.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiError {
    final private HttpStatus status;
    final private LocalDateTime timestamp = LocalDateTime.now();
    private String message;
    private String debugMessage;
}
