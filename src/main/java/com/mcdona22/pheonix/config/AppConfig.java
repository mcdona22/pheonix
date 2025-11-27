package com.mcdona22.pheonix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
import java.util.function.Supplier;

@Configuration
public class AppConfig {

    @Bean
    public Supplier<String> entityIdSupplier() {
        return () -> UUID.randomUUID().toString();
    }
}
