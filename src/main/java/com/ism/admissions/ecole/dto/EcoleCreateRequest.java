package com.ism.admissions.ecole.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EcoleCreateRequest(
        @NotBlank(message = "Le code est obligatoire")
        @Size(max = 10, message = "Le code ne doit pas dépasser 10 caractères")
        String code,

        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
        String nom
) {
}
