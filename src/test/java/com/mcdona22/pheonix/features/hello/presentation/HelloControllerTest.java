package com.mcdona22.pheonix.features.hello.presentation;

import com.mcdona22.pheonix.features.hello.application.HelloService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Tag("unit")

@ExtendWith(MockitoExtension.class)
public class HelloControllerTest {
    HelloController controller;
    @Mock
    private HelloService mockService;

    @BeforeEach
    void setUp() {
        controller = new HelloController(mockService);
    }

    @Test
    public void testServiceCalled() {
        final var expected = new HelloResponse("message", "version");

        when(mockService.getHelloResponse()).thenReturn(expected);

        final var actual = controller.index();

        assertEquals(actual, expected, "incorrect return value used");
        verify(mockService, times(1)).getHelloResponse();

    }
}
