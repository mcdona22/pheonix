package com.mcdona22.pheonix.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ApiErrorTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Test
    @DisplayName("ApiError object can be created correctly")
    public void testApiErrorInstantiation() {
        final var expectedStatus = HttpStatus.CREATED;
        final var expectedMessage = "message";
        final var expectedDebugMessage = "debugMessage";

        final var apiError = new ApiError(expectedStatus, expectedMessage, expectedDebugMessage);

        assertNotNull(apiError);
        assertEquals(expectedStatus, apiError.getStatus());
        assertEquals(expectedMessage, apiError.getMessage());
        assertEquals(expectedDebugMessage, apiError.getDebugMessage());
        assertNotNull(apiError.getTimestamp());

        logger.info("Created ApiError {}", apiError);
    }
}
