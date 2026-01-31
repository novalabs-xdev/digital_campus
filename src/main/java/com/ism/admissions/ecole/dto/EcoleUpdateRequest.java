package com.ism.admissions.ecole.dto;

import jakarta.validation.constraints.NotBlank;

public record EcoleUpdateRequest(
        @NotBlank(message = "Le nom est obligatoire")
        String nom,

        boolean actif
) {
}
