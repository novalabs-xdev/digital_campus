package com.ism.admissions.auth.passwordreset.exception;

public class OtpExpiredException extends RuntimeException {
    public OtpExpiredException() {
        super("L'OTP a expiré. Veuillez demander un nouveau code.");
    }
}
