package com.ism.admissions.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @Email(message = "Email invalide")
        @NotBlank(message = "Email obligatoire")
        String email,

        @NotBlank(message = "Mot de passe obligatoire")
        @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
        String password,

        @NotBlank
        String fullName,

        @NotBlank
        String phone,

        @NotBlank
        String country
) {
}
