package com.ism.admissions.candidat.service;

import com.ism.admissions.candidat.domain.Candidat;

import java.util.Optional;

public interface CandidatService {
    Candidat creerCandidat(Candidat candidat);

    Optional<Candidat> rechercherParEmail(String email);

    Candidat creerCandidatAvecCompte(Candidat candidat);

}
