package com.jshop.exception;

import jakarta.servlet.http.HttpServletResponse;

public class ResourceNotFoundException extends AbstractApplicationException {

    public ResourceNotFoundException(String s) {
        super(s, HttpServletResponse.SC_NOT_FOUND);
    }
}
