package com.flexigp.admissions.candidature.exception;

import com.flexigp.admissions.candidature.domain.StatutCandidature;

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
