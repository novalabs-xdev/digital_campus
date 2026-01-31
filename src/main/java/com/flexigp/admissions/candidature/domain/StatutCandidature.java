package com.flexigp.admissions.candidature.domain;

public enum StatutCandidature {
    BROUILLON,     // saisie en cours
    SOUMISE,       // dossier soumis
    A_VALIDER,     // pré-validé par l’agent
    VALIDEE,       // validé par le directeur
    REJETEE,
    EN_ATTENTE;
}
