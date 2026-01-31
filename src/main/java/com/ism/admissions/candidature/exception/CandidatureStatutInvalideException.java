package com.ism.admissions.candidature.exception;

import com.ism.admissions.candidature.domain.StatutCandidature;

public class CandidatureStatutInvalideException extends RuntimeException {
    public CandidatureStatutInvalideException(String message) {
        super(message);
    }

    public CandidatureStatutInvalideException(
            StatutCandidature statutActuel,
            String action
    ) {
        super(
                "Action '" + action + "' impossible avec le statut actuel : " + statutActuel
        );
    }
}
