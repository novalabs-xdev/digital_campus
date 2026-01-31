package com.ism.admissions.classe.dto;

import jakarta.validation.constraints.NotBlank;

public record ClasseUpdateRequest(
        @NotBlank(message = "Le libellé est obligatoire")
        String libelle,

        Integer capacite,

        boolean actif
) {
}
