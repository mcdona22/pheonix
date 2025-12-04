package com.mcdona22.pheonix.common;


import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public FilterRegistrationBean<@NonNull TrailingSlashRemovingFilter> trailingSlashRemovingBean() {
        final var bean = new TrailingSlashRemovingFilter();
        FilterRegistrationBean<@NonNull TrailingSlashRemovingFilter> registrationBean =
                new FilterRegistrationBean<>(bean);
        registrationBean.addUrlPatterns("/*");

        logger.info("Trailing Slash Removing Filter Registered: {}", registrationBean);
        registrationBean.setOrder(10);

        return registrationBean;
    }
}