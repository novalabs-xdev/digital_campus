package com.ism.admissions.admission.dto;

public record StatistiquesClasseResponse(
        Long classeId,
        String classeNom,
        Integer capacite,
        int nombreAdmis,
        int placesRestantes,
        double tauxRemplissage
) {
}
