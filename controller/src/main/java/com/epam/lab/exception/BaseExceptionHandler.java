package com.epam.lab.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseExceptionHandler.class);

    protected ExceptionInfo handleAnyException(final Exception e, final HttpServletRequest request) {
        ExceptionInfo info = new ExceptionInfo(request.getRequestURL().toString(), e.getLocalizedMessage());
        LOGGER.error("Handling exception on the top-level", e);
        return info;
    }

}
