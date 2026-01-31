package com.ism.admissions.auth.passwordreset.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException() {
        super("Le token de réinitialisation a expiré.");
    }}
