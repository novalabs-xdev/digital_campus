package com.ism.admissions.auth.passwordreset.exception;

public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException() {
        super("Token de réinitialisation invalide.");
    }
}
