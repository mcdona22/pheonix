package com.mcdona22.pheonix.features.hello.presentation;


import com.mcdona22.pheonix.features.hello.application.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HelloController {

    private final HelloService helloService;

    @GetMapping("/")
    public HelloResponse index() {
        return helloService.getHelloResponse();
//        return new HelloResponse("Hello from Boot", "1.0");
    }
}
