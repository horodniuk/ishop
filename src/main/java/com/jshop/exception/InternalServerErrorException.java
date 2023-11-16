package com.jshop.exception;

import jakarta.servlet.http.HttpServletResponse;

public class InternalServerErrorException extends AbstractApplicationException {

    public InternalServerErrorException(String message) {
        super(message, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    public InternalServerErrorException(Throwable cause) {
        super(cause, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
