package com.ism.admissions.classe.dto;

import com.ism.admissions.ecole.dto.EcoleResponse;

import java.time.LocalDateTime;

public record ClasseResponse(
        Long id,
        String code,
        String libelle,
        EcoleResponse ecole,
        Integer capacite,
        boolean actif,
        LocalDateTime createdAt
) {
}
