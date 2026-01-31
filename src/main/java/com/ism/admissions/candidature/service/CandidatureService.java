package com.ism.admissions.candidature.service;

public interface CandidatureService {
    void soumettreCandidature(Long candidatureId); // Envoie le mail de confirmation + n° suivi

    void preValiderParAgent(Long candidatureId);

    void validerParDirecteur(Long candidatureId); // Mutation compte + mail accès étudiant

    void rejeterParDirecteur(Long candidatureId, String motif);
}