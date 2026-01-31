package com.flexigp.admissions.candidature.service;

public interface CandidatureService {
    void soumettreCandidature(Long candidatureId);
    void preValiderParAgent(Long candidatureId);
    void validerParDirecteur(Long candidatureId);

    void rejeterParDirecteur(Long candidatureId, String motif);
}
