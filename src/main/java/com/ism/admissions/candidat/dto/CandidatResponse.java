package com.ism.admissions.candidat.dto;

import com.ism.admissions.common.domain.Sexe;

import java.time.LocalDate;

public record CandidatResponse(
        Long id,
        String nom,
        String prenom,
        Sexe sexe,
        LocalDate dateNaissance,
        String nationalite,
        String email,
        String telephone
) {
}
