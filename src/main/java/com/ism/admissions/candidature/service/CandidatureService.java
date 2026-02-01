package com.ism.admissions.candidature.service;

import com.ism.admissions.candidature.domain.Candidature;
import com.ism.admissions.candidature.domain.StatutCandidature;

import java.util.List;

public interface CandidatureService {
    void soumettreCandidature(Long candidatureId);

    void preValiderParAgent(Long candidatureId);

    void validerParDirecteur(Long candidatureId);

    void rejeterParDirecteur(Long candidatureId, String motif);

    List<Candidature> listerCandidatures(StatutCandidature statut, Long ecoleId, Long classeId);

    Candidature getCandidature(Long id);
}