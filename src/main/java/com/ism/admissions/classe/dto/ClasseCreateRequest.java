package com.ism.admissions.classe.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClasseCreateRequest(
        @NotBlank(message = "Le code est obligatoire")
        String code,

        @NotBlank(message = "Le libellé est obligatoire")
        String libelle,

        @NotNull(message = "L'école est obligatoire")
        Long ecoleId,

        @Min(value = 1, message = "La capacité doit être au moins 1")
        Integer capacite
) {
}
