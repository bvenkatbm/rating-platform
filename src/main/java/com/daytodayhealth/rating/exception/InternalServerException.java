package com.daytodayhealth.rating.exception;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String exception) {
        super(exception);
    }
}
