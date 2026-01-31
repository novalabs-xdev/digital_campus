package com.ism.admissions.exception.business;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String email) {
        super("Email déjà utilisé : " + email);
    }
}
