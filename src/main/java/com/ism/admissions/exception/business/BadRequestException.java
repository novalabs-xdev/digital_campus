package com.ism.admissions.exception.business;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}