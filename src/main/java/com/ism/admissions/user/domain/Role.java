package com.ism.admissions.user.domain;

public enum Role {
    ADMIN,
    AGENT, // Celui qui pré-valide
    DIRECTEUR, // Celui qui valide l'admission
    SUPERVISEUR,
    CANDIDAT,  // Role initial lors de l'inscription
    ETUDIANT   // Role après validation finale (Mutation)
}
