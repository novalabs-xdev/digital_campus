package com.flexigp.admissions.auth.passwordreset.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record VerifyOtpRequest(
        @NotBlank(message = "L'email est requis")
        String email,

        @NotBlank(message = "L'OTP est requis")
        @Pattern(regexp = "\\d{6}", message = "L'OTP doit contenir 6 chiffres")
        String otp
) {
}
