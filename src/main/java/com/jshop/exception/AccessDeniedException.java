package com.jshop.exception;

import jakarta.servlet.http.HttpServletResponse;

public class AccessDeniedException extends AbstractApplicationException {

    public AccessDeniedException(String s) {
        super(s, HttpServletResponse.SC_FORBIDDEN);
    }
}
