package com.flexigp.admissions.historique_candidature.dto;

import com.flexigp.admissions.candidature.domain.StatutCandidature;

import java.time.LocalDateTime;

public record HistoriqueStatutResponse(
        StatutCandidature ancienStatut,
        StatutCandidature nouveauStatut,
        String action,
        LocalDateTime dateAction,
        String effectuePar
) {
}
