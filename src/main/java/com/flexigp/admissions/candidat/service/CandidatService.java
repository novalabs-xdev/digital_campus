package com.flexigp.admissions.candidat.service;

import com.flexigp.admissions.candidat.domain.Candidat;

import java.util.Optional;

public interface CandidatService {
    Candidat creerCandidat(Candidat candidat);

    Optional<Candidat> rechercherParEmail(String email);

}
