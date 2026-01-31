package com.flexigp.admissions.auth.passwordreset.exception;

public class TokenAlreadyUsedException extends RuntimeException {
    public TokenAlreadyUsedException() {
        super("Ce token a déjà été utilisé.");
    }
}
