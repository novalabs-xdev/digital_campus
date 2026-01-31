package com.flexigp.admissions.auth.passwordreset.exception;

public class OtpInvalidException extends RuntimeException {
    public OtpInvalidException() {
        super("Code OTP invalide.");
    }
}
