package com.jshop.exception;

import jakarta.servlet.http.HttpServletResponse;

public class ValidationException extends AbstractApplicationException {

    public ValidationException(String s) {
        super(s, HttpServletResponse.SC_BAD_REQUEST);
    }
}
