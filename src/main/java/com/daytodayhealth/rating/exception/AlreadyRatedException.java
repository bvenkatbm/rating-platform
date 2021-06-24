package com.daytodayhealth.rating.exception;

public class AlreadyRatedException extends RuntimeException {
    public AlreadyRatedException(String message) {
        super(message);
    }
}
