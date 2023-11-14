package com.jshop.exception;

public class AccessDeniedException extends IllegalArgumentException {

    public AccessDeniedException(String message) {
        super(message);
    }
}
