package com.flexigp.admissions.historique_candidature.service;

import com.flexigp.admissions.candidature.domain.Candidature;
import com.flexigp.admissions.candidature.domain.StatutCandidature;
import com.flexigp.admissions.historique_candidature.domain.HistoriqueStatutCandidature;

import java.util.List;

public interface HistoriqueCandidatureService {
    void enregistrerTransition(
            Candidature candidature,
            StatutCandidature ancienStatut,
            StatutCandidature nouveauStatut,
            String action,
            String effectuePar
    );

    List<HistoriqueStatutCandidature> consulterHistorique(Long candidatureId);
}
