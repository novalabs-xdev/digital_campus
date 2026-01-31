package com.ism.admissions.candidature.dto;

import com.ism.admissions.candidat.dto.CandidatResponse;
import com.ism.admissions.candidature.domain.StatutCandidature;
import com.ism.admissions.classe.dto.ClasseResponse;
import com.ism.admissions.ecole.dto.EcoleResponse;

import java.time.LocalDateTime;
import java.util.List;

public record CandidatureDetailResponse(
        Long id,
        CandidatResponse candidat,
        EcoleResponse ecole,
        ClasseResponse classe,
        StatutCandidature statut,
        LocalDateTime dateCreation,
        String creePar,
        List<DocumentResponse> documents
) {
}
