package com.mcdona22.pheonix.common;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class TrailingSlashRemovingFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        final var request = (HttpServletRequest) servletRequest;
        final var response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        logger.info("Entering doFilter for {}", requestURI);
        if (requestURI.length() > 1 && requestURI.endsWith("/")) {
            logger.info("Trailing slash detected for {}", requestURI);
            String redirectUrl = request.getRequestURL().toString();
            redirectUrl = redirectUrl.substring(0,
                                                redirectUrl.length() - 1
                                               );
            // Remove the slash from the URL buffer
            if (request.getQueryString() != null) {
                redirectUrl += "?" + request.getQueryString();
            }

            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY); // HTTP 301
            response.setHeader("Location", redirectUrl);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
