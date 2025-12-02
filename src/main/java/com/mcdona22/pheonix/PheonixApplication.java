package com.mcdona22.pheonix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PheonixApplication {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    static void main(String[] args) {
        SpringApplication.run(PheonixApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(ApplicationContext ctx) {
        return args -> {
//            String[] beanNames = ctx.getBeanDefinitionNames();
//
//            Arrays.sort(beanNames);
//            for(String beanName : beanNames) {
//                System.out.println(beanName);
//            }
            logger.info("Pheonix application has been initialized");

        };
    }

}
