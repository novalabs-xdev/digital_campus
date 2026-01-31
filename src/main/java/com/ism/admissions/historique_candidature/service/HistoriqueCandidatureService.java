package com.ism.admissions.historique_candidature.service;

import com.ism.admissions.candidature.domain.Candidature;
import com.ism.admissions.candidature.domain.StatutCandidature;
import com.ism.admissions.historique_candidature.domain.HistoriqueStatutCandidature;

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
