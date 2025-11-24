package com.mcdona22.pheonix.features.hello.application;

import com.mcdona22.pheonix.features.hello.presentation.HelloResponse;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public HelloResponse getHelloResponse() {
        return new HelloResponse("Hello from Boot", "1.0");

    }
}
