package com.flexigp.admissions.auth.passwordreset.dto;

public record VerifyOtpResponse(
        String token,
        String message
) {
}
