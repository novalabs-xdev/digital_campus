package com.ism.admissions.admission.exception;

public class CandidatureNotValideeException extends RuntimeException {
    public CandidatureNotValideeException(String message) {
        super(message);
    }

    public CandidatureNotValideeException(Long candidatureId) {
        super("La candidature id=" + candidatureId + " n’est pas validée");
    }
}
