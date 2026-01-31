package com.ism.admissions.ecole.dto;

import java.time.LocalDateTime;

public record EcoleResponse(
        Long id,
        String code,
        String nom,
        boolean actif,
        LocalDateTime createdAt
) {
}
