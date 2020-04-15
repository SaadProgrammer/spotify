package com.example.spotify.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class LoggingController extends AbstractRequestLoggingFilter {

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        logger.info(message);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        logger.info(message);
    }
}
