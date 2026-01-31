package com.ism.admissions.candidature.exception;

public class CandidatureNotFoundException extends RuntimeException {
    public CandidatureNotFoundException(String message) {
        super(message);
    }

    public CandidatureNotFoundException(Long id) {
        super("Candidature introuvable (id=" + id + ")");
    }
}
